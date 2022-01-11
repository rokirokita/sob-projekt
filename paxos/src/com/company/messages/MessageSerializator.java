package com.company.messages;

import java.io.*;

public class MessageSerializator {
    public static byte[] serialize(Serializable serializable) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(serializable);
            out.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Object deserialize(byte[] bytes) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (ois != null) try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
