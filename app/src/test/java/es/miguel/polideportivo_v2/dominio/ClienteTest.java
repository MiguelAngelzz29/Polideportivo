package es.miguel.polideportivo_v2.dominio;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClienteTest {

    @Mock
    private ArrayList<ContrataInstalacion> mockListaContrataciones;

    @Mock
    private ArrayList<ReservaPista> mockListaReservaPistas;

    @Mock
    private ArrayList<ReservaActividad> mockListaReservaActividades;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructorWithAllFields() {
        String id_cliente = "123";
        String nombre = "John";
        String primer_apellido = "Doe";
        String segundo_apellido = "Smith";
        String direccion = "123 Main St";
        String email = "john.doe@example.com";
        String telefono = "123456789";
        String password = "password";
        int tipo_abono = 1;

        Cliente cliente = new Cliente(id_cliente, nombre, primer_apellido, segundo_apellido,
                direccion, email, telefono, password, tipo_abono);

        assertEquals(id_cliente, cliente.getId_cliente());
        assertEquals(nombre, cliente.getNombre());
        assertEquals(primer_apellido, cliente.getPrimer_apellido());
        assertEquals(segundo_apellido, cliente.getSegundo_apellido());
        assertEquals(direccion, cliente.getDireccion());
        assertEquals(email, cliente.getEmail());
        assertEquals(telefono, cliente.getTelefono());
        assertEquals(password, cliente.getPassword());
        assertEquals(tipo_abono, cliente.getTipo_abono());
    }

    @Test
    public void testConstructorWithRequiredFields() {
        String id_cliente = "123";
        String nombre = "John";
        String primer_apellido = "Doe";
        String segundo_apellido = "Smith";
        String direccion = "123 Main St";
        String email = "john.doe@example.com";
        String telefono = "123456789";
        int tipo_abono = 1;

        Cliente cliente = new Cliente(id_cliente, nombre, primer_apellido, segundo_apellido,
                direccion, email, telefono, tipo_abono);

        assertEquals(id_cliente, cliente.getId_cliente());
        assertEquals(nombre, cliente.getNombre());
        assertEquals(primer_apellido, cliente.getPrimer_apellido());
        assertEquals(segundo_apellido, cliente.getSegundo_apellido());
        assertEquals(direccion, cliente.getDireccion());
        assertEquals(email, cliente.getEmail());
        assertEquals(telefono, cliente.getTelefono());
        assertEquals(tipo_abono, cliente.getTipo_abono());
    }

}