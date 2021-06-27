package ufv.dis.final2021;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.HasItems;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    ArrayList<Ip> arraymio = new ArrayList<>();
    Ip ip =new Ip();
    FuncionesIP objetoip = new FuncionesIP();
    TratamientoJson arrayips = new TratamientoJson();
    ArrayList arrayconips = arrayips.leerFicheroJson();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();



        //-------------
        //LONG TO IP

        // Texto que se muestra en pantalla y donde se puede escribir
        TextField textolong = new TextField("Escribe longip");

        //Invisible para el usuario y sirve para imprimir en pantalla c

        Button buttonlong = new Button("Enviar", event -> layout.addComponent(new Label(objetoip.longToIp(Long.parseLong(textolong.getValue())))));

        Button buttonlongarray = new Button("Enviar", event -> layout.addComponent(new Label(devolverstringip(Long.parseLong(textolong.getValue())))));


        //DOTTED TO IP
        TextField textodotted = new TextField("Escribe dottedip");
        Button buttondotted = new Button("Enviar");

        buttondotted.addClickListener(c->{

            try {
                layout.addComponent(new Label(devolverGeo(textodotted.getValue())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //AÑADIR
        Grid<Ip> grid = new Grid<>(Ip.class);
        TextField ip_from = new TextField("Escribe ip_from");
        TextField ip_to = new TextField("Escribe ip_to");
        TextField country_code = new TextField("Escribe country_code");
        TextField region_name = new TextField("Escribe region_name");
        TextField city_name = new TextField("Escribe city_name");
        TextField latitude = new TextField("Escribe latitude");
        TextField longitude = new TextField("Escribe longitude");
        TextField zip_code = new TextField("Escribe zip_code");
        TextField time_zone = new TextField("Escribe time_zone");

        Button buttonarray = new Button("añadir array");
        buttonarray.addClickListener(c->{
            arrayconips.add(new Ip( Long.valueOf(String.valueOf(ip_from.getValue())),Long.valueOf(String.valueOf(ip_to.getValue())),(String.valueOf(country_code.getValue())),(String.valueOf(region_name.getValue())),(String.valueOf(city_name.getValue())),Double.valueOf(String.valueOf(latitude.getValue())),Double.valueOf(String.valueOf(longitude.getValue())),(String.valueOf(zip_code.getValue())),(String.valueOf(time_zone.getValue()))));
            grid.getDataProvider().refreshAll();
        });
//ELIMINAR
        TextField eliminar = new TextField("Escribe cityname a eliminar");
        Button buttoneliminar = new Button("eliminar del array");
        buttoneliminar.addClickListener(c->{
            int index= 0;
            try {
                index = getIndexByProperty(String.valueOf(eliminar.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            layout.addComponent(new Label(String.valueOf(index)));
            arrayconips.remove(index);
            grid.getDataProvider().refreshAll();
        });
//EDITAR
        TextField editar = new TextField("Seleccion elemento a editar mediante el cityname.");
        Button buttoneditar = new Button("editar elemento del array");
        buttoneditar.addClickListener(c->{
            //eliminamos el objeto del index
            int index= 0;
            try {
                index = getIndexByProperty(String.valueOf(editar.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //creamos el objeto en el index
            arrayconips.remove(index);
            arrayconips.add(index,new Ip(Long.valueOf(String.valueOf(ip_from.getValue())),Long.valueOf(String.valueOf(ip_to.getValue())),
                    (String.valueOf(country_code.getValue())),(String.valueOf(region_name.getValue())),(String.valueOf(city_name.getValue())),
                    Double.valueOf(String.valueOf(latitude.getValue())),Double.valueOf(String.valueOf(longitude.getValue())),
                    (String.valueOf(zip_code.getValue())),(String.valueOf(time_zone.getValue()))));
            grid.getDataProvider().refreshAll();
        });
        // Añade las cosas en pantalla
        grid.setColumns("ip_from", "ip_to", "country_code", "region_name","city_name","latitude","longitude","zip_code","time_zone");
        grid.setItems(arrayconips);
        layout.addComponents(textolong, buttonlong,buttonlongarray, textodotted, buttondotted,ip_from,ip_to,country_code,region_name
                ,city_name,latitude,longitude,zip_code,time_zone,buttonarray,eliminar,buttoneliminar,editar,buttoneditar,grid);

        setContent(layout);





    }


    //Funcion que busca objeto del array segun string dado y devuelve el index del objeto segun la condicion
    public int getIndexByProperty(String yourString) throws Exception{
        Boolean found=false;
        Ip founitem=null;
        int i =0;
        while (!found && i<=arrayconips.size())
        {
            Ip item= (Ip) arrayconips.get(i);
            if (item.getCity_name().equals(yourString)){
                found=true;
                founitem=item;
            }else
                i++;
        }
        if (founitem!=null){
            return i;
        }else throw new Exception("Elemento no encontrado");
    }

    //FUNCIONES IP
    public String devolverGeo(String ipdoted) throws Exception {
        //TEST EXCEPCION IP INEXISTENTE
        if (ipdoted==""){
            throw new Exception("Ip vacio");
        }

        int flag=calcularmax(ipdoted);
        if (flag==1){
            throw new Exception("fueraderangoporarriba");
        }
// llamada a la funcionn
        Long ipLong = FuncionesIP.Dot2LongIP(ipdoted);
        if (ipLong==0L){
            throw new Exception("fueraderangoporabajo");
        }

        //Creacion del objeto
        Ip ip = new Ip();
        //Array que contiene los arrays del fichero json
        ArrayList<Ip> ips = TratamientoJson.leerFicheroJson();
        //Iterador
        Iterator iterator_ips = ips.iterator();
//While no se acaba el array
        while (iterator_ips.hasNext()) {
            //Vete recorriendo el array y guardalo en la variable ip
            ip = (Ip) iterator_ips.next();
            //Condicion del enunciado
            //Si la ip
            if (ipLong >= ip.getIp_from() && ipLong <= ip.getIp_to()) {
                return ip.toString();
            }
        }
        return ip.toString();
    }

    //FUNCIONES IP PERO CON LONG
    public String devolverstringip(long iplong) {
        //Creacion del objeto
        Ip ip = new Ip();
        //Array que contiene los arrays del fichero json
        ArrayList<Ip> ips = TratamientoJson.leerFicheroJson();
        //Iterador
        Iterator iterator_ips = ips.iterator();
//While no se acaba el array
        while (iterator_ips.hasNext()) {
            //Vete recorriendo el array y guardalo en la variable ip
            ip = (Ip) iterator_ips.next();
            //Condicion del enunciado
            //Si la ip
            if (iplong >= ip.getIp_from() && iplong <= ip.getIp_to()) {
                return ip.toString();
            }
        }
        return ip.toString();
    }

    //FUNCION PARA EL TEST
    public int calcularmax(String ipdoted){
        FuncionesIP funcip=new FuncionesIP();
        int max=255;
        int flag=0;
        String[] parts = ipdoted.split("\\.");
        if (Integer.valueOf(parts[0])>max)
            flag=1;
        if (Integer.valueOf(parts[1])>max)
            flag=1;
        if (Integer.valueOf(parts[2])>max)
            flag=1;
        if (Integer.valueOf(parts[3])>max)
            flag=1;
        return flag;
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
