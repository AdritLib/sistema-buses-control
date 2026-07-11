package com.sistema_buses.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema_buses.client.ConductorClient;
import com.sistema_buses.client.IncidenciaClient;
import com.sistema_buses.client.RecorridoConductorClient;
import com.sistema_buses.dto.asignacion.AsignacionConductorResponse;
import com.sistema_buses.dto.incidencia.IncidenciaConductorRequest;
import com.sistema_buses.dto.paradero.ParaderoConductorResponse;
import com.sistema_buses.dto.recorrido.IniciarRecorridoRequest;
import com.sistema_buses.dto.recorrido.MarcarLlegadaRequest;
import com.sistema_buses.dto.recorrido.RecorridoConductorResponse;

import feign.FeignException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/conductor")
public class ConductorViewController {

    private static final int HISTORIAL_SIZE = 10;

    private final ConductorClient conductorClient;
    private final RecorridoConductorClient recorridoClient;
    private final IncidenciaClient incidenciaClient;

    public ConductorViewController(
            ConductorClient conductorClient,
            RecorridoConductorClient recorridoClient,
            IncidenciaClient incidenciaClient) {
        this.conductorClient = conductorClient;
        this.recorridoClient = recorridoClient;
        this.incidenciaClient = incidenciaClient;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        validarSesionConductor(session, model, "dashboard");

        model.addAttribute("sinAsignacion", false);
        try {
            Long usuarioId = usuarioId(session);
            
            AsignacionConductorResponse asignacion = conductorClient.obtenerAsignacionActiva(usuarioId);
            RecorridoConductorResponse ultimoRecorrido = cuerpo(
                    recorridoClient.obtenerUltimoPorAsignacion(asignacion.getId()));
            model.addAttribute("asignacion", asignacion);
            model.addAttribute("ultimoRecorrido", ultimoRecorrido);
            model.addAttribute("recorridoActivo", estaEnCurso(ultimoRecorrido) ? ultimoRecorrido : null);
            model.addAttribute("recorridoFinalizado", estaFinalizado(ultimoRecorrido));
            model.addAttribute("recorridoNoIniciado", estaNoIniciado(ultimoRecorrido));
        } catch (FeignException.UnprocessableEntity ex) {
            model.addAttribute("sinAsignacion", true);
        } catch (RuntimeException ex) {
            model.addAttribute("sinAsignacion", true);
        }
        return "conductor/dashboard";
    }

    @GetMapping("/mi-ruta")
    public String miRuta(HttpSession session, Model model) {
        String redirect = validarSesionConductor(session, model, "mi-ruta");
        if (redirect != null) return redirect;

        try {
            Long usuarioId = usuarioId(session);
            AsignacionConductorResponse asignacion = conductorClient.obtenerAsignacionActiva(usuarioId);
            RecorridoConductorResponse recorrido = cuerpo(
                    recorridoClient.obtenerUltimoPorAsignacion(asignacion.getId()));
            
            model.addAttribute("asignacion", asignacion);
            model.addAttribute("ruta", asignacion.getRuta());
            model.addAttribute("recorrido", recorrido);
            model.addAttribute("recorridoFinalizado", estaFinalizado(recorrido));
            model.addAttribute(
                    "paraderos",
                    recorrido != null
                            ? recorridoClient.listarParaderos(recorrido.getId())
                            : paraderosDeRuta(asignacion));
        } catch (RuntimeException ex) {
            model.addAttribute("error", mensajeError(ex));
            model.addAttribute("paraderos", Collections.emptyList());
        }
        return "conductor/mi-ruta";
    }

    @GetMapping("/recorrido")
    public String recorrido(HttpSession session, Model model) {
        validarSesionConductor(session, model, "recorrido");
        //if (redirect != null) return redirect;

        try {
            Long usuarioId = usuarioId(session);
            AsignacionConductorResponse asignacion = conductorClient.obtenerAsignacionActiva(usuarioId);
            
            RecorridoConductorResponse ultimoRecorrido = cuerpo(
                    recorridoClient.obtenerUltimoPorAsignacion(asignacion.getId()));
            
            RecorridoConductorResponse recorridoActivo = estaEnCurso(ultimoRecorrido) ? ultimoRecorrido : null;
            
            List<ParaderoConductorResponse> paraderos = recorridoActivo != null
                    ? recorridoClient.listarParaderos(recorridoActivo.getId())
                    : Collections.emptyList();

            model.addAttribute("asignacion", asignacion);
            model.addAttribute("ultimoRecorrido", ultimoRecorrido);
            model.addAttribute("recorridoActivo", recorridoActivo);
            model.addAttribute("recorridoFinalizado", estaFinalizado(ultimoRecorrido));
            model.addAttribute("recorridoNoIniciado", estaNoIniciado(ultimoRecorrido));
            model.addAttribute("paraderosRecorrido", paraderos);
            model.addAttribute("proximoParaderoID", proximoParaderoPendiente(paraderos));
            model.addAttribute("todosParaderosMarcados", todosParaderosMarcados(paraderos));
        } catch (RuntimeException ex) {
            model.addAttribute("error", mensajeError(ex));
            model.addAttribute("paraderosRecorrido", Collections.emptyList());
        }
        return "conductor/recorrido";
    }

    @PostMapping("/recorrido/iniciar")
    public String iniciarRecorrido(
            @RequestParam Long asignacionID,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String redirect = validarSesionConductor(session, null, "recorrido");
        if (redirect != null) return redirect;

        try {
            recorridoClient.iniciar(new IniciarRecorridoRequest(asignacionID));
            redirectAttributes.addFlashAttribute("exito", "Recorrido iniciado correctamente.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", mensajeError(ex));
        }
        return "redirect:/conductor/recorrido";
    }

    @PostMapping("/recorrido/paradero/{idRutaParadero}/llegada")
    public String marcarLlegada(
            @PathVariable Long idRutaParadero,
            @RequestParam(required = false) String observaciones,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String redirect = validarSesionConductor(session, null, "recorrido");
        if (redirect != null) return redirect;

        try {
            RecorridoConductorResponse recorridoActivo = cuerpo(
                    recorridoClient.obtenerActivo(usuarioId(session)));
            if (recorridoActivo == null) {
                throw new IllegalStateException("No tienes un recorrido en curso.");
            }
            recorridoClient.marcarLlegada(
                    recorridoActivo.getId(),
                    idRutaParadero,
                    new MarcarLlegadaRequest(observaciones));
            redirectAttributes.addFlashAttribute("exito", "Llegada registrada.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", mensajeError(ex));
        }
        return "redirect:/conductor/recorrido";
    }

    @PostMapping("/recorrido/finalizar")
    public String finalizarRecorrido(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirect = validarSesionConductor(session, null, "recorrido");
        if (redirect != null) return redirect;

        try {
            RecorridoConductorResponse recorridoActivo = cuerpo(
                    recorridoClient.obtenerActivo(usuarioId(session)));
            if (recorridoActivo == null) {
                throw new IllegalStateException("No tienes un recorrido en curso.");
            }
            recorridoClient.finalizar(recorridoActivo.getId());
            redirectAttributes.addFlashAttribute("exito", "Recorrido finalizado correctamente.");
            return "redirect:/conductor/historial";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", mensajeError(ex));
            return "redirect:/conductor/recorrido";
        }
    }

    @GetMapping("/incidencias/nueva")
    public String incidenciaForm(HttpSession session, Model model) {
        String redirect = validarSesionConductor(session, model, "incidencias");
        if (redirect != null) return redirect;

        prepararFormularioIncidencia(session, model, new IncidenciaConductorRequest());
        return "conductor/incidencia-form";
    }

    @PostMapping("/incidencias")
    public String registrarIncidencia(
            @ModelAttribute("incidenciaForm") IncidenciaConductorRequest request,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        String redirect = validarSesionConductor(session, model, "incidencias");
        if (redirect != null) return redirect;

        if (request.getRecorridoID() == null || request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
            model.addAttribute("error", "Debes indicar el recorrido activo y la descripción de la incidencia.");
            prepararFormularioIncidencia(session, model, request);
            return "conductor/incidencia-form";
        }

        try {
            incidenciaClient.registrarComoConductor(request);
            redirectAttributes.addFlashAttribute("exito", "Incidencia reportada correctamente.");
            return "redirect:/conductor/recorrido";
        } catch (RuntimeException ex) {
            model.addAttribute("error", mensajeError(ex));
            prepararFormularioIncidencia(session, model, request);
            return "conductor/incidencia-form";
        }
    }

    @GetMapping("/historial")
    public String historial(
            @RequestParam(defaultValue = "0") int pagina,
            HttpSession session,
            Model model) {
        String redirect = validarSesionConductor(session, model, "historial");
        if (redirect != null) return redirect;

        int paginaSegura = Math.max(pagina, 0);
        try {
            model.addAttribute(
                    "recorridos",
                    conductorClient.obtenerHistorial(
                            usuarioId(session),
                            paginaSegura,
                            HISTORIAL_SIZE));
        } catch (RuntimeException ex) {
            model.addAttribute("error", mensajeError(ex));
            model.addAttribute("recorridos", Collections.emptyList());
        }
        model.addAttribute("paginaActual", paginaSegura);
        return "conductor/historial";
    }

    @GetMapping("/historial/{idRecorrido}")
    public String detalleHistorial(
            @PathVariable Long idRecorrido,
            HttpSession session,
            Model model) {
        String redirect = validarSesionConductor(session, model, "historial");
        if (redirect != null) return redirect;

        try {
            RecorridoConductorResponse recorrido = recorridoClient.obtenerDetalle(idRecorrido);
            model.addAttribute("recorrido", recorrido);
            model.addAttribute("asignacion", null);
            model.addAttribute("ruta", recorrido.getRuta());
            model.addAttribute("vehiculo", recorrido.getVehiculo());
            model.addAttribute("paraderos", recorridoClient.listarParaderos(idRecorrido));
        } catch (RuntimeException ex) {
            model.addAttribute("error", mensajeError(ex));
            model.addAttribute("paraderos", Collections.emptyList());
        }
        return "conductor/historial-detalle";
    }

    private void prepararFormularioIncidencia(
            HttpSession session,
            Model model,
            IncidenciaConductorRequest formulario) {
        try {
            RecorridoConductorResponse recorrido = cuerpo(
                    recorridoClient.obtenerActivo(usuarioId(session)));
            if (recorrido != null && formulario.getRecorridoID() == null) {
                formulario.setRecorridoID(recorrido.getId());
            }
            model.addAttribute("recorridoActivo", recorrido);
        } catch (RuntimeException ex) {
            model.addAttribute("recorridoActivo", null);
            model.addAttribute("error", mensajeError(ex));
        }
        model.addAttribute("incidenciaForm", formulario);
    }

    private String validarSesionConductor(HttpSession session, Model model, String menuActivo) {
        if (session.getAttribute("token") == null || usuarioId(session) == null) {
            return "redirect:/login";
        }
        String rol = String.valueOf(session.getAttribute("rol"));
        if (!"CONDUCTOR".equalsIgnoreCase(rol)) {
            return "redirect:/login";
        }
        if (model != null) {
            model.addAttribute("menuActivo", menuActivo);
            model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
            model.addAttribute("nombreConductor", session.getAttribute("nombreUsuario"));
            model.addAttribute("rol", rol.toUpperCase());
        }
        return null;
    }

    private boolean estaEnCurso(RecorridoConductorResponse recorrido) {
        return recorrido != null && "EN_CURSO".equalsIgnoreCase(recorrido.getEstado());
    }

    private boolean estaFinalizado(RecorridoConductorResponse recorrido) {
        return recorrido != null && "FINALIZADO".equalsIgnoreCase(recorrido.getEstado());
    }

    private boolean estaNoIniciado(RecorridoConductorResponse recorrido) {
        return recorrido == null || recorrido.getEstado() == null || recorrido.getEstado().isBlank();
    }

    private Long proximoParaderoPendiente(List<ParaderoConductorResponse> paraderos) {
        if (paraderos == null) return null;
        
        return paraderos.stream()
                .filter(paradero -> !llego(paradero))
                .findFirst()
                .map(ParaderoConductorResponse::getRutaParaderoID)
                .orElse(null);
    }

    private boolean todosParaderosMarcados(List<ParaderoConductorResponse> paraderos) {
        return paraderos != null
                && !paraderos.isEmpty()
                && paraderos.stream().allMatch(this::llego);
    }

    private boolean llego(ParaderoConductorResponse paradero) {
        return paradero != null
                && ("LLEGO".equalsIgnoreCase(paradero.getEstadoLlegada())
                        || paradero.getFechaHoraLlegada() != null);
    }

    private Long usuarioId(HttpSession session) {
        Object usuarioId = session.getAttribute("usuarioId");
        if (usuarioId instanceof Long valor) {
            return valor;
        }
        if (usuarioId instanceof Number valor) {
            return valor.longValue();
        }
        if (usuarioId instanceof String valor && !valor.isBlank()) {
            return Long.parseLong(valor);
        }
        return null;
    }

    private RecorridoConductorResponse cuerpo(ResponseEntity<RecorridoConductorResponse> response) {
        if (response == null || !response.getStatusCode().is2xxSuccessful() || !response.hasBody()) {
            return null;
        }
        return response.getBody();
    }

    private List<ParaderoConductorResponse> paraderosDeRuta(AsignacionConductorResponse asignacion) {
        if (asignacion == null || asignacion.getRuta() == null || asignacion.getRuta().getParaderos() == null) {
            return Collections.emptyList();
        }
        return asignacion.getRuta().getParaderos();
    }

    private String mensajeError(RuntimeException ex) {
        if (ex instanceof FeignException feignException) {
            if (feignException.status() == 403 || feignException.status() == 401) {
                return "Tu sesión no tiene permisos para realizar esta operación.";
            }
            if (feignException.status() == 204) {
                return "No se encontraron datos para esta consulta.";
            }
            String detalle = feignException.contentUTF8();
            String mensaje = extraerCampoJson(detalle, "detail");
            return mensaje != null
                    ? mensaje
                    : (detalle == null || detalle.isBlank()
                            ? "No fue posible comunicarse con el backend."
                            : "El backend respondió con un error: " + detalle);
        }
        return ex.getMessage() != null ? ex.getMessage() : "Ocurrió un problema al procesar la operación.";
    }

    private String extraerCampoJson(String json, String campo) {
        if (json == null || json.isBlank()) {
            return null;
        }
        String marcador = "\"" + campo + "\":\"";
        int inicio = json.indexOf(marcador);
        if (inicio < 0) {
            return null;
        }
        inicio += marcador.length();
        StringBuilder valor = new StringBuilder();
        boolean escapado = false;
        for (int i = inicio; i < json.length(); i++) {
            char caracter = json.charAt(i);
            if (escapado) {
                valor.append(caracter);
                escapado = false;
            } else if (caracter == '\\') {
                escapado = true;
            } else if (caracter == '"') {
                break;
            } else {
                valor.append(caracter);
            }
        }
        return valor.length() == 0 ? null : valor.toString();
    }
}
