package Dags2D.utils;

import Dags2D.Space;

import java.io.*;
import java.util.Base64;

public class DAGUtils {
//    public void exportAsString(Space space) throws IOException {
//        String str = "files\\obj.txt";
//        Path path = Path.of(str).toAbsolutePath();
//
//        String
//        StringStream outputStream = new FileOutputStream(path.toString());
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//        objectOutputStream.writeObject(space);
//
//        objectOutputStream.close();
//    }

    public String exportAsString(Space space) throws IOException {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutputStream oStream = new ObjectOutputStream(bStream);
        oStream.writeObject(space);
        oStream.close();

        return Base64.getEncoder().encodeToString(bStream.toByteArray());
    }

    public Space importFromString(String str) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(str);
        ObjectInputStream oiStream = new ObjectInputStream(new ByteArrayInputStream(data));

        Object obj = oiStream.readObject();
        if (obj.getClass() != Space.class) {
            throw new ClassNotFoundException();
        }

        oiStream.close();
        return (Space) obj;
    }

//    public Space importFromString(String s) throws IOException, ClassNotFoundException {
//        String str = "files\\obj.txt";
//        Path path = Path.of(str).toAbsolutePath();
//
//        FileInputStream fileInputStream = new FileInputStream(path.toString());
//        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//        Space space = (Space) objectInputStream.readObject();
//
//        System.out.println(space);
//
//        return space;
//    }
}
