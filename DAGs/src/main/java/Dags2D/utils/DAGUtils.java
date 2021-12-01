package Dags2D.utils;

import Dags2D.Space;

import java.io.*;
import java.util.Base64;

public class DAGUtils {
    public String exportAsBinaryString(Space space) throws IOException {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutputStream oStream = new ObjectOutputStream(bStream);
        oStream.writeObject(space);
        oStream.close();

        return Base64.getEncoder().encodeToString(bStream.toByteArray());
    }

    public Space importFromBinaryString(String str) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(str);
        ObjectInputStream oiStream = new ObjectInputStream(new ByteArrayInputStream(data));

        Object obj = oiStream.readObject();
        if (obj.getClass() != Space.class) {
            throw new ClassNotFoundException();
        }

        oiStream.close();
        return (Space) obj;
    }

    public Space importFromString(String str) {
        return (Space) Space.createFromStringRepresent(str);
    }

    public String exportAsString(Space space) {
        return space.stringRepresent();
    }
}
