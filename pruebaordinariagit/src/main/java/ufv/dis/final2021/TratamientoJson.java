package ufv.dis.final2021;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.*; //gson
import com.google.gson.reflect.TypeToken;


public class TratamientoJson {

    private static final String dir_json = new File("LocalizaIP.json").getAbsolutePath();

    public static void crearFicheroJson(ArrayList<Ip> videotecas){
        try{
            FileWriter mywriter = new FileWriter(dir_json);
            mywriter.write(new Gson().toJson(videotecas));
            mywriter.close();
        }catch (Exception ex){
            System.out.println(ex);
        }

    }


    public static ArrayList<Ip> leerFicheroJson(){
        ArrayList<Ip> arrayips = new ArrayList<Ip>();
        Gson gson = new Gson();
        try {
            arrayips = gson.fromJson(new FileReader(dir_json),new TypeToken<ArrayList<Ip>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrayips;
    }



}
