package ufv.dis.final2021;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.String;


public class main {

    public static void main(String[] args){
    ArrayList<Ip> ips = new ArrayList<>();

    ips = TratamientoJson.leerFicheroJson();

    Ip ip=new Ip();
/*
    for(int i = 0; i < ips.size(); i++) {
        System.out.print(ips);
    }
/*
    FuncionesIP funcip=new FuncionesIP();
    String dottedIP="255.255.255.256";
    String[] parts = dottedIP.split("\\.");
        System.out.print(parts[0]);
        //4294967295*/

        /*
        Boolean found=false;
        Ip founitem=null;
        int i =0;

        while (!found && i<ips.size())
        {
            Ip item= (Ip) (Ip) ips.get(i);
            if (item.getCity_name().equals("Tottori")){
                found=true;
                founitem=item;
            }
            i++;
        }
        if (founitem!=null){
            ips.remove(i);


    }
        for(int j = 0; j < ips.size(); j++) {
            System.out.print(ips);
        }
*/

        FuncionesIP funcionesIP=new FuncionesIP();
        String ipmax="255.255.255.255";
        String ipmax1="255.255.255.256";
        String ipmenos="0.0.0.0.0";
        System.out.println(funcionesIP.Dot2LongIP(ipmax));
        System.out.println(funcionesIP.Dot2LongIP(ipmax1));
        System.out.println(funcionesIP.Dot2LongIP(ipmenos));
}




}
