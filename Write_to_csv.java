import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Write_to_csv {
    StringBuilder fileOut;


    public Write_to_csv(long[] values) {
        fileOut = new StringBuilder();
    }

    public void writeToCsv() {

        String output_filename = "Grid_Input_bash_algorithm_timing";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(output_filename));
            pw.write(fileOut.toString());
            pw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
    }

    public StringBuilder buildString(long[] values) {

        for (int i = 0; i < values.length; ++i) {
            switch (i) {

                case 0: {
                    fileOut.append("BR,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 1: {
                    fileOut.append("BRM,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 2: {
                    fileOut.append("BRMW,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }

                case 3: {
                    fileOut.append("SR,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 4: {
                    fileOut.append("SRM,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 5: {
                    fileOut.append("BRS,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 6: {
                    fileOut.append("BRMS,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 7: {
                    fileOut.append("BRMWS,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }

                case 8: {
                    fileOut.append("SRS,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 9: {
                    fileOut.append("SRMS,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 10: {
                    fileOut.append("BRMF,");
                    fileOut.append((int) values[i] * Math.pow(10, -6));
                    break;
                }
                case 11: {
                    fileOut.append("BRMWF,");
                    fileOut.append((int) values[i] * Math.pow(10, 6));
                    break;
                }
                case 12: {
                    fileOut.append("SRMF,");
                    fileOut.append((int) values[i] * Math.pow(10, 6));
                    break;
                }
                default: {
                    throw new RuntimeException("Unknown Algorithm");
                }
            }
            fileOut.append("\n");
        }
        fileOut.append("\n");
        return fileOut;
    }
}
