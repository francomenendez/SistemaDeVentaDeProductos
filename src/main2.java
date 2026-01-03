import logica.excepciones.PersistenciaException;
import poolconexiones.*;

public class main2 {
    public static void main(String[] args) {
        try {
            // Crear el pool de conexiones
            IPoolConexiones pool = new PoolConexiones();
            
            // Crear múltiples hilos que simulan clientes solicitando conexiones
            Thread[] hilos = new Thread[8]; // Más hilos que conexiones disponibles
            
            for (int i = 0; i < hilos.length; i++) {
                final int clienteId = i;
                hilos[i] = new Thread(() -> {
                    try {
                        System.out.println("Cliente " + clienteId + " solicitando conexión...");
                        
                        // Solicitar conexión
                        IConexion conn = pool.obtenerConexion(true);
                        System.out.println("Cliente " + clienteId + " obtuvo conexión");
                        
                        // Simular trabajo con la base de datos
                        Thread.sleep((long)(Math.random() * 2000));
                        
                        // Liberar conexión
                        System.out.println("Cliente " + clienteId + " liberando conexión...");
                        pool.liberarConexion(conn, true);
                        System.out.println("Cliente " + clienteId + " liberó conexión");
                        
                        // Esperar un poco y solicitar otra conexión
                        Thread.sleep(500);
                        System.out.println("Cliente " + clienteId + " solicitando segunda conexión...");
                        conn = pool.obtenerConexion(false);
                        System.out.println("Cliente " + clienteId + " obtuvo segunda conexión");
                        Thread.sleep((long)(Math.random() * 1000));
                        pool.liberarConexion(conn, true);
                        System.out.println("Cliente " + clienteId + " terminó");
                        
                    } catch (PersistenciaException | InterruptedException e) {
                        System.out.println("Error en cliente " + clienteId + ": " + e.getMessage());
                    }
                });
            }
            
            // Iniciar todos los hilos
            System.out.println("Iniciando prueba con " + hilos.length + " clientes...");
            for (Thread hilo : hilos) {
                hilo.start();
            }
            
            // Esperar a que todos los hilos terminen
            for (Thread hilo : hilos) {
                hilo.join();
            }
            
            System.out.println("Prueba completada.");
            
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
