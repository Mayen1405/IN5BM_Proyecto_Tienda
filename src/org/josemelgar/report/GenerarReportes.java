/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.josemelgar.report;
import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.josemelgar.db.Conexion;

/**
 *
 * @author Jose
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reportesMain = (JasperReport) JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reportesMain, parametros, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

/*
interface Map
    hash map es uno de los objetos que implementa un conjunto de key-value .
    tiene un constructor sin parametros new HasMap() y su finalidad suele
    referirse para agrupar informacion en un solo objeto 

    Esto tiene cierta similitud con la coleccion de objetos (ArrayList) pero con
    la diferencia de que estos no tienen orden
    
    Hash hace referencia a una tecnica de organizacion de archivos , Hashing
    (abierto-cerrado) en la que se almacena registro en una direccion que es generada
    por una funcion se aplica a la llave del registro

    en java el hash map posee un espacio de memoria y caundo se guarda un objeto
    en dicho espacio se determina la direccion aplicando una funcion a la llave 
    que le indiquemos  
*/
