package zoo;

import java.sql.Connection;;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZoologicoDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/zoologico";
    private static final String USER = "root";
    private static final String PASSWORD = "javi123";

    public static void main(String[] args) {
        
        insertarAnimal(1, "Zebra", "10-03-2005", "Zebra Negra");
        mostrarAnimales();
        actualizarAnimal(1, "Zebra Guatemalteca");
        eliminarAnimal(1);
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertarAnimal(int id, String nombre, String fechaNacimiento, String especie) {
        String sql = "INSERT INTO animales (identificador, nombre, fecha_nacimiento, especie) VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, fechaNacimiento);
            pstmt.setString(4, especie);
            pstmt.executeUpdate();
            System.out.println("Animal insertado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar animal: " + e.getMessage());
        }
    }

    public static void mostrarAnimales() {
        String sql = "SELECT * FROM animales";

        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("identificador"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Fecha de nacimiento: " + rs.getDate("fecha_nacimiento"));
                System.out.println("Especie: " + rs.getString("especie"));
               
            }
        } catch (SQLException e) {
            
    }

    public static void actualizarAnimal(int id, String nuevoNombre) {
        String sql = "UPDATE animales SET nombre = ? WHERE identificador = ?";

        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Animal actualizado.");
            } else {
                System.out.println("No coincide el  ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar animal: " + e.getMessage());
        }
    }

    public static void eliminarAnimal(int id) {
        String sql = "DELETE FROM animales WHERE identificador = ?";

        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Animal eliminado con éxito.");
            } else {
                System.out.println("No coincide el ID " + id);
            }

        }
    }
}
