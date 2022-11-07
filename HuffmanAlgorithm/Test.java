package HuffmanAlgorithm;

public class Test {

    public static void main(String[] args) {
        byte[] data = new byte[3];
        long long1 = 0b1101000010011; //13 bit
        long long2 = 0b11100111; //8 bit
        long long3 = 0b010; //3 bit

        //8 første bit fra long1 til data[0]
        //øvrige bits maskeres bort med &

        /**
         * MASKERING
         * 0b1101000010011   long1
         * &      11111111   de bit-ene vi vil maskere
         * ---------------
         * =         10011   resultat
         */

        //Eksempel
        /*System.out.println("Før maskering: "+Long.toBinaryString(long1));
        System.out.println("Etter maskering: "+Long.toBinaryString((long1 & 0b11111111)));*/
        data[0] = (byte)(long1 & 0b11111111);


        //5 gjenværende bit fra long1 til data[1]
        //høyreskiftet fjerner bits vi allerede har lagt i data[0]
        //trenger ikke maskere fordi resterende bits i long1 er 0.

        /**
         * HØYRESKIFT
         * 1101000010011 >> 8 = 0000000011010 = 11010
         */
        //Eksempel
        /*System.out.println(Long.toBinaryString(long1));
        System.out.println(Long.toBinaryString(long1 >> 8));*/
        data[1] = (byte)(long1 >> 8);


        //data[1] har plass til 3 av de 8 bit fra long2
        //venstreskifter 5 plasser fordi de 5 første bits i data[1] er i bruk fra før
        //trenger ikke maskere vekk bits fordi bits over 256 ikke går inn i en byte uansett

        data[1] |= (byte)(long2 << 5);
        //5 gjenværende bit fra long2 til data[2]
        //høyreskift fjerner de bits vi allerede la i data[1]
        data[2] = (byte)(long2 >> 3);
        //data[2] har plass til de 3 bit fra long3
        data[2] |= (byte)(long3 << 5);
        //System.out.printf("%x %x %x\n", data[0], data[1], data[2]);

    }
}
