/* BCIS 3680 Forta Database Application
   User Interface Tier
   Author: Cole Dombrowski
*/

package dombrowski.cole.forta;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class FortaGUI extends Application
{
    // Variables
    String developer = "Cole Dombrowski";
    Label lblTitle;
    Label lblSelTab, lblSelCol, lblTable, lblColumns, lblResults;
    RadioButton rdoCustomers, rdoVendors, rdoProducts, rdoOrders;
    ToggleGroup tgTables;
    ComboBox<String> cboColumns;
    ObservableList<String> colNames, data;
    ListView<String> lsvData;
    TextArea taResults; // Unused for now
    Button btnSearch, btnClose;

    // Containers for three sections
    VBox vbTables, vbColumns, vbOutput;
    // Container for entire window
    HBox hbDisplay;

    Scene scnMain;

    // For ListView contents
    String[] lsv_initial = {"Query result to be", "displayed here."};

    // For column lists
    String[] cust_cols = {"cust_id", "cust_name", "cust_address", "cust_city",
        "cust_state", "cust_zip", "cust_country", "cust_contact", "cust_email" };
    String[] vend_cols = {"vend_id", "vend_name", "vend_address", "vend_city",
        "vend_state", "vend_zip", "vend_country"};
    String[] prod_cols = {"prod_id", "vend_id", "prod_name", "prod_price", 
        "prod_desc"};
    String[] order_cols = {"order_num", "order_date", "cust_id"};

    // For table and column names to pass to DB Access methods
    String sel_table, sel_column;

    ArrayList<String> results;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        // Set up title
        lblTitle = new Label(developer + " HW4");
        lblTitle.setStyle("-fx-text-fill: blue;-fx-font-size: 10pt;");
        
        // Set up table radio buttons
        lblSelTab = new Label("\nSelect a Table:");
        tgTables = new ToggleGroup();

        rdoCustomers = new RadioButton("Customers");
        rdoVendors = new RadioButton("Vendors");
        rdoProducts = new RadioButton("Products");
        rdoOrders = new RadioButton("Orders");

        rdoCustomers.setToggleGroup(tgTables);
        rdoVendors.setToggleGroup(tgTables);
        rdoProducts.setToggleGroup(tgTables);
        rdoOrders.setToggleGroup(tgTables);
       
        // Set up columns combo inital view
        lblSelCol = new Label("\n\nSelect a Column:");
        lblSelCol.setAlignment(Pos.TOP_LEFT);
        cboColumns = new ComboBox<>();
        cboColumns.getItems().add("Click a Table First");
        cboColumns.getSelectionModel().selectFirst();
        
        // Update columns combo based on radio button clicked
        rdoCustomers.setOnAction(event ->
        {
            colNames = convStrItems(cust_cols);
            cboColumns.getItems().setAll(colNames);
            cboColumns.getSelectionModel().selectFirst();
        });
        rdoVendors.setOnAction(event ->
        {
            colNames = convStrItems(vend_cols);
            cboColumns.getItems().setAll(colNames);
            cboColumns.getSelectionModel().selectFirst();
        });
        rdoProducts.setOnAction(event ->
        {
            colNames = convStrItems(prod_cols);
            cboColumns.getItems().setAll(colNames);
            cboColumns.getSelectionModel().selectFirst();
        });
        rdoOrders.setOnAction(event ->
        {
            colNames = convStrItems(order_cols);
            cboColumns.getItems().setAll(colNames);
            cboColumns.getSelectionModel().selectFirst();
        });

        // Left section - tables
        vbTables = new VBox(10, lblTitle, 
                lblSelTab, rdoCustomers, rdoVendors, rdoProducts, rdoOrders);
        vbTables.setPadding(new Insets(30));

        // Middle section - columns and buttons
        btnSearch = new Button("Search");
        btnClose = new Button("Close");
        vbColumns = new VBox(15, lblSelCol, cboColumns, btnSearch, btnClose);

        // Right section - results
        lblResults = new Label("\n\nSearch Results:");
        lsvData = new ListView();
        lsvData.setPrefSize(130, 230); // Width, Height
        vbOutput = new VBox(10, lblResults, lsvData);

        // Run query when Search button is clicked
        btnSearch.setOnAction(event ->
        {
            // Get table name
            if (rdoCustomers.isSelected()) { sel_table = "customers"; }
            if (rdoVendors.isSelected()) { sel_table = "vendors"; }
            if (rdoProducts.isSelected()) { sel_table = "products"; }
            if (rdoOrders.isSelected()) { sel_table = "orders"; }

            // Get column name
            sel_column = cboColumns.getValue();

            // Get results
            results = DBAccess.retrieveData(sel_table, sel_column);
            // Update list view
            data = convALItems(results);
            lsvData.getItems().setAll(data);
        });

        // Exit when Close button is clicked
        btnClose.setOnAction(event ->
        {
            primaryStage.close();
        });

        // Set up window
        hbDisplay = new HBox(30, vbTables, vbColumns, vbOutput);
        scnMain = new Scene(hbDisplay, 538, 328);

        // Show window
        primaryStage.setScene(scnMain);
        primaryStage.setTitle("BCIS3680 Demo | Forta Database");
        primaryStage.show();
    }

    // Auxiliary methods to prepare ObservableLists
    // Convert String array to ObservableList
    public static ObservableList convStrItems(String[] str)
    {
        ObservableList<String> oblStr
                = FXCollections.observableArrayList(str);
        return oblStr;
    }

    // Convert ArrayList to ObservableList
    public static ObservableList convALItems(ArrayList alst)
    {
        ObservableList<String> oblAlst
                = FXCollections.observableArrayList(alst);
        return oblAlst;
    }
}