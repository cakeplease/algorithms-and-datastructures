package HuffmanAlgorithm;
//en klasse for å sende bit strings til fil
public class BitString {
    //hvor mange av bitene som er med
    long sequence;
    int length;


    public BitString(long bitSeq, int actualLength) {
        sequence = bitSeq;
        length = actualLength;
    }

    @Override
    public String toString() {
        return "Bit sequence: "+Long.toBinaryString(sequence)+" Sequence length: "+length;
    }


    //med høyre/venstre skift og & og | operasjoner kan vi få bitstrengene inn i et byte array
    //lesing: vi leser inn et byte-array og plukker ut en og en bit for å navigere gjennom huffmantreet
}
