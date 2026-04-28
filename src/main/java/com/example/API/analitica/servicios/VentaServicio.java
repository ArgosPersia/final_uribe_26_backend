package com.example.API.analitica.servicios;

import com.example.API.analitica.modelos.Venta;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class VentaServicio {

    public String validarConPython(Venta venta) {
        RestTemplate restTemplate = new RestTemplate();
        String urlPython = "http://localhost:8000/analizar-venta";

        try {
            // Enviamos la venta a Python y recibimos el mapa con los datos limpios
            ResponseEntity<Map> respuesta = restTemplate.postForEntity(urlPython, venta, Map.class);
            Map<String, Object> resultado = respuesta.getBody();

            if (resultado != null && resultado.containsKey("estado")) {
                // ACTUALIZAMOS los datos en Java con lo que Python mande limpio
                venta.setVendedor(resultado.get("vendedor").toString());
                venta.setTalla(resultado.get("talla").toString());
                venta.setProducto(resultado.get("producto").toString());

                // Si Python devolvió una cantidad corregida
                if (resultado.containsKey("cantidad")) {
                    venta.setCantidad(Integer.parseInt(resultado.get("cantidad").toString()));
                }

                return resultado.get("estado").toString();
            }
            return "ERROR_DATOS";

        } catch (Exception e) {
            System.out.println("Error conectando a Python: " + e.getMessage());
            return "ERROR_CONEXION";
        }
    }
}