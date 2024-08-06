import java.io.*;

import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.System.setOut;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
import java.util.Scanner;

public class FileListMaker {
    static ArrayList<String> list = new ArrayList<>();
    static Scanner in = new Scanner(System.in);
    static boolean viewMode = false;
    static boolean needsToSave = false; // self-explanatory.
    static boolean agreedToSave = false; // brought up when user attempts to quit or load while needsToSave.
    public static void main(String[] args)
    {
        //three steps to begin
        //display the list
        //display the menu
        //menu input

        final String mainMenu = "A - add  | D - delete | I - insert   | V - view" +
                                "\nM - move | O - open   | S - save     | C - clear list | Q - quit";
        String menuCmd = "";
        boolean done = false;
        boolean fileIsNamed = false;
        do{
            viewList();
            viewMode = false;
            menuCmd = SafeInput.getRegExString(in, mainMenu, "^[AaDdIiVvQqCcMmSsOo]$");
            menuCmd = menuCmd.toUpperCase();

            switch(menuCmd) {
                case "A":
                    addToList();
                    needsToSave = true;
                    break;
                case "D":
                    if(list.size() != 0)
                    {
                        deleteFromList();
                        needsToSave = true;
                    }
                    else System.out.println("\nNo items yet to delete!\n");
                    break;
                case "I":
                    if(list.size() != 0)
                    {
                        insertInList();
                        needsToSave = true;
                    }
                    else System.out.println("\nList empty. Try | A - add | instead!\n");
                    break;
                case "V":
                    if (list.size() != 0) {
                        viewMode = true;
                        System.out.printf("\n%61s", "View Mode\n");
                    } else System.out.println("\nNo list yet to View!\n");
                    break;
                case "Q":
                    if(needsToSave)
                    {
                        agreedToSave = SafeInput.getYNConfirm(in, "List is unsaved. Want to save?");
                        if(agreedToSave == true)
                        {
                            saveList();
                            done = true;
                        }else done = SafeInput.getYNConfirm(in,"Really quit?");
                    }else done = SafeInput.getYNConfirm(in,"Really quit?");
                    break;
                    // below are the new ones.  let's do this!
                case "M":
                            //this will involve a little placeholder spot so you can delete its old self
                            //before placing the new one down.
                            //let's do this one after "C"
                    if(list.size() != 0)
                    {
                        needsToSave = true;
                        moveItemAroundList();
                    }
                    else System.out.println("\nNo items yet to Move!\n");
                    break;
                case "O":
                            //this will be the hardest part... i still don't understand how
                            // to parse the info or use the delimiter.
                            //clearList(); I put this inside of the openList method instead in the "if file chosen,"
                            //so no deletion if no file chosen.
                    if(needsToSave)
                    {
                        agreedToSave = SafeInput.getYNConfirm(in, "List is unsaved. Want to save?");
                        if(agreedToSave == true) saveList();
                    }
                    if(list.size() != 0) clearList();
                    openList();
                    break;
                case "S":
                            //this will involve "printing" out the list using the Writer and following the same guidelines.
                            //it won't be hard, probably.
                            //you'll have to call this method when you ask to save other places as well -- keep in mind.
                    if(list.size() != 0) saveList();
                    else out.println("\nNo list yet to Save!\n");
                    break;
                case "C":
                    if (list.size() != 0)
                    {
                    boolean surelyClearing = SafeInput.getYNConfirm(in, "Are you sure?" +
                            " List will be lost.");
                    if (surelyClearing) clearList();
                    }else System.out.println("\nNo list yet to Clear!\n");
                    break;
            }

        }while(!done);
        System.out.println("\nGoodbye!\n");

    }

    private static void viewList()
    {
        for(int b = 0; b < 31; b++) System.out.print("*+");
        System.out.println("");

        if(list.size() != 0)
        {
            if(viewMode == true) // "view mode"
            {
                for (int i = 0; i < list.size(); i++) System.out.printf("%60s\n", list.get(i));
                String printMessage = "Total Items: ";
                System.out.printf("%50s %9d\n", printMessage, list.size());
            }
            else // regular list mode
            {
                for (int i = 0; i < list.size(); i++) System.out.printf("%3d | %55s\n", i + 1, list.get(i));
            }

        }else
        {
            System.out.println("\n++++ Welcome to the FileListMaker! Use the menu to begin. ++++\n");
        }

        for(int b = 0; b < 31; b++) System.out.print("+*"); // reversed order for style points
        System.out.println("");
    }

    private static void addToList() {
        boolean readyToAdd = false;
        String newItem = "";
        do{
            newItem = SafeInput.getNonZeroLenString(in, "Enter item");
            if (newItem.length() <= 55) {
                list.add(newItem);
                readyToAdd = true;
                System.out.println("Adding item...\n");
            } else System.out.println("Length of item is too long! Keep under 55 characters.");
        }while(!readyToAdd);

    }

    private static void deleteFromList()
    {
        int itemToDelete = SafeInput.getRangedInt(in, "Enter the number of the item " +
                "to delete", 1, list.size());
        itemToDelete = itemToDelete - 1; // could just say itemToDelete-- but this is easier to read for me
        list.remove(itemToDelete);
        System.out.println("Deleting item...\n");
    }

    private static void insertInList()
    {
        boolean readyToInsert = false;
        String insertedItem = "";
        int insertLocation = SafeInput.getRangedInt(in, "Enter the number " +
                "at which to insert", 1, list.size());
        insertLocation = insertLocation - 1;
        do
        {
            insertedItem = SafeInput.getNonZeroLenString(in, "Enter item");
            if (insertedItem.length() <= 55) {
                list.add(insertLocation, insertedItem);
                readyToInsert = true;
                System.out.println("Inserting item...\n");
            } else System.out.println("Length of item is too long! Keep under 55 characters.");
        }while(!readyToInsert);
    }
    private static void clearList()
    {
        for (int i = (list.size() - 1); i > -1; i--)
        {
            list.remove(i);
        }
    }

    private static void moveItemAroundList()
    {
        String itemInStasis = "";
        int itemToBeMoved = SafeInput.getRangedInt(in, "Enter the number of the item " +
                "to move", 1, list.size());
        itemToBeMoved = itemToBeMoved - 1;
        itemInStasis = list.get(itemToBeMoved); // string being given  data from the int var's index on the list
        //System.out.println("The item you're talking about is " + itemMoveStasis + ". anyway...\n");

        int newMoveLocation = SafeInput.getRangedInt(in,"Moving to", 1, list.size());
        newMoveLocation = newMoveLocation - 1;

        list.remove(itemToBeMoved);
        list.add(newMoveLocation, itemInStasis);
        System.out.println("Deleting item...\n");
    }
    private static void saveList()
    {
        String fileName = SafeInput.getNonZeroLenString(in, "Enter file name under which to save (without extension)");
        String userNamedfilePath = "/src/" + fileName + ".txt"; // forward slashes because I'm on Mac

        //now we put the formatted list into an array
        ArrayList<String> recs = new ArrayList<>();
        String recArray = "";

        for (int i = 0; i < list.size(); i++)
        {
            recArray = String.format("%3d - %55s", i + 1, list.get(i));
            recs.add(recArray);
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + userNamedfilePath);
        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for(String rec : recs)
            {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("List saved!");
            needsToSave = false;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    private static void openList()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> lines = new ArrayList<>();

        final int FIELDS_LENGTH = 2;
        int openListNum;
        String openListItem;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                while (reader.ready()) // while the reader has something to read
                {
                    rec = reader.readLine(); // this is the actual line reading
                    lines.add(rec);
                }
                reader.close(); // do not forget to close the file. "seals it + flushes buffer"
                out.println("\n List opened, processing...");

                String[] fields;
                for(String l:lines) // now to count the words
                {
                    fields = l.split(" - ");
                    if(fields.length == FIELDS_LENGTH)
                    {
                        openListNum = Integer.parseInt(fields[0].trim()) - 1;
                        openListItem = fields[1].trim();
                        list.add(openListNum, openListItem);
                    }else System.out.println("Trouble parsing file. Hold on...");
                } needsToSave = false;
            }else
            {
                out.println("File not chosen.");
                needsToSave = false;
            }
        }
        catch(FileNotFoundException fnfe)
        {
            out.println("File not found, sorry.");
            fnfe.printStackTrace();
        }
        catch(IOException ioe)
        {

        }
    }
}