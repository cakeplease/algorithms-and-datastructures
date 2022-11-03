package HuffmanAlgorithm;
//en klasse for å sende bit strings til fil
public class BitString {
    //hvor mange av bitene som er med
    long bitSequence;
    int bitLength;


    public BitString(long bitSeq, int actualLength) {
        bitSequence = bitSeq;
        bitLength = actualLength;
    }


    //med høyre/venstre skift og & og | operasjoner kan vi få bitstrengene inn i et byte array
    //lesing: vi leser inn et byte-array og plukker ut en og en bit for å navigere gjennom huffmantreet
}
