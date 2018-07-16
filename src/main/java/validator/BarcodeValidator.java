package validator;

import com.itextpdf.text.pdf.*;

// funkcje sprawdzajace zgodnosc z danym kodem oraz przechowywanie komunikatow o bledach
public class BarcodeValidator {
    private String errorMessage;
    private String barcode;

    public boolean validateBarcode(String content, Barcode barcode){
        if (barcode == null){
            return isBarcodeQR(content);
        }
        else {
            switch (barcode.getCodeType()) {
                case 9: //128
                    return isBarcode128(content);
                case 12: //CODABAR
                    return isBarcodeCodabar(content);
                case 1: case 2: // EAN
                    return isBarcodeEAN(content);
                case 7: //POSTNET
                    return isBarcodePostnet(content);
                case 0: // Inter25 i 39
                    if (barcode instanceof Barcode39)
                        return isBarcode39(content);
                    else
                        return isBarcodeInter25(content);
                default:
                    return false;
            }
        }
    }

    private boolean isEmpty(String barcode){
        if (barcode.length() == 0){
            errorMessage = "No characters";
            this.barcode = barcode;
            return true;
        }

        return false;
    }

    private boolean isBarcode128(String barcode) {
        if (isEmpty(barcode) == true){
            return false;
        }

        char[] barcodeCharacters = barcode.toCharArray();
        for (char c : barcodeCharacters) {
            if (c > 255) {
                errorMessage = "Invalid characters";
                this.barcode = barcode;
                return false;
            }
        }

        return true;
    }

    private boolean isBarcode39(String barcode) {
        if (isEmpty(barcode) == true){
            return false;
        }

        char[] barcodeCharacters = barcode.toCharArray();
        for (char c : barcodeCharacters) {
            if (c != '-' && c != '$' && c != '*' && c != '/' && c != '.' && c != '+' && c != '%' && c != ' ' &&
                    (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
                errorMessage = "Invalid characters. Allowed are - $ * / . + % space or A-Z or 0-9";
                this.barcode = barcode;
                return false;
            }
        }

        return true;
    }

    private boolean isBarcodeCodabar(String barcode) {
        if (isEmpty(barcode) == true){
            return false;
        }

        char[] barcodeCharacters = barcode.toCharArray();
        for (char c : barcodeCharacters) {
            if (c != '-' && c != '$' && c != '*' && c != '/' && c != '.' && c != '+' && c != '%' && c != ' ' &&
                    (c < '0' || c > '9') && (c != 'A' && c != 'B' && c != 'C' && c != 'D')) {
                errorMessage = "Invalid characters. Allowed are - $ * / . + % space or 0-9";
                this.barcode = barcode;
                return false;
            }
        }

        if (barcodeCharacters[0] != 'A' && barcodeCharacters[0] != 'B' && barcodeCharacters[0] != 'C' && barcodeCharacters[0] != 'D') {
            errorMessage = "Invalid start character. Codabar must have one of 'ABCD' as start character";
            this.barcode = barcode;
            return false;
        }


        if (barcodeCharacters[barcodeCharacters.length - 1] != 'A' && barcodeCharacters[barcodeCharacters.length - 1] != 'B' &&
                barcodeCharacters[barcodeCharacters.length - 1] != 'C' && barcodeCharacters[barcodeCharacters.length - 1] != 'D') {
            errorMessage = "Invalid stop character. Codabar must have one of 'ABCD' as stop character";
            this.barcode = barcode;
            return false;
        }

        return true;
    }

    private boolean isBarcodeEAN(String barcode) {
        if (isEmpty(barcode) == true){
            return false;
        }

        if (barcode.length() != 13) {
            errorMessage = "Invalid barcode length. EAN must have 13 characters";
            this.barcode = barcode;
            return false;
        }

        char[] barcodeCharacters = barcode.toCharArray();
        for (char c : barcodeCharacters) {
            if (c < '0' || c > '9') {
                errorMessage = "Invalid characters. Allowed are 0-9";
                this.barcode = barcode;
                return false;
            }
        }

        return true;
    }

    private boolean isBarcodeInter25(String barcode) {
        if (isEmpty(barcode) == true){
            return false;
        }

        if (barcode.length() % 2 == 1) {
            errorMessage = "Invalid barcode length. Inter25 must have even number of characters";
            this.barcode = barcode;
            return false;
        }

        return true;
    }

    private boolean isBarcodePostnet(String barcode) {
        if (isEmpty(barcode) == true){
            return false;
        }

        if (barcode.length() < 5 || barcode.length() > 13){
            errorMessage = "Invalid barcode lenght. POSTNET must have 5-13 characters";
            this.barcode = barcode;
            return false;
        }

        char[] barcodeCharacters = barcode.toCharArray();
        for (char c : barcodeCharacters){
            if (c < '0' || c > '9'){
                errorMessage = "Invalid characters. Allowed are 0-9";
                this.barcode = barcode;
                return false;
            }
        }

        return true;
    }

    private boolean isBarcodeQR(String barcode) { // ?
        if (isEmpty(barcode) == true){
            return false;
        }

        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getBarcode() {
        return barcode;
    }
}