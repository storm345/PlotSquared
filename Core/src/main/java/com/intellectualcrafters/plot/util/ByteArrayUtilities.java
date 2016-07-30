package com.intellectualcrafters.plot.util;

/**
 * A collection of utility methods, helping
 * with conversion between byte arrays and
 * generic types
 */
public class ByteArrayUtilities {

    /**
     * Convert an integer to a byte array
     *
     * @param i Integer to convert
     * @return A byte array, the length will always be 4
     */
    public static byte[] integerToBytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >> 24);
        bytes[1] = (byte) (i >> 16);
        bytes[2] = (byte) (i >> 8);
        bytes[3] = (byte) (i);
        return bytes;
    }

    /**
     * Convert a byte array to an integer.
     * This will read the first 4 bytes.
     *
     * @param bytes A byte array with at least 4 objects
     * @return The extracted integer
     */
    public static int bytesToInteger(byte[] bytes) {
        return (bytes[0]<<24)&0xff000000|(bytes[1]<<16)&0x00ff0000|(bytes[2]<<8)&0x0000ff00|(bytes[3])&0x000000ff;
    }

    /**
     * Convert a byte array to boolean.
     * This will read the first byte.
     *
     * @param bytes A byte array with at least 1 object
     * @return The extracted boolean
     */
    public static boolean bytesToBoolean(byte[] bytes) {
        return bytes[0] == 1;
    }

    /**
     * Convert a boolean to a byte array
     *
     * @param b Boolean to convert
     * @return A byte array, the length will always be 1
     */
    public static byte[] booleanToBytes(boolean b) {
        return new byte[] {(byte)(b ? 1 : 0)};
    }
}
