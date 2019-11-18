/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.FlowShopScheduler;
import model.Job;
import model.Machine;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Kelas FSSSimulatorController Kelas yang mnjembatani hubungan anatara package
 * view dan package model Berfungsi untuk mengontrol perangkat lunak
 *
 * @author Tedi Tri Ramdani 2015730001
 */
public class FSSSimulatorViewController implements Initializable {

    @FXML
    private RadioButton fileRB;
    @FXML
    private RadioButton randomRB;
    @FXML
    private TextField nJobsTF;
    @FXML
    private TextField mMachinesTF;
    @FXML
    private Button genData;
    @FXML
    private Button openFile;
    @FXML
    private Label labelFile;
    @FXML
    private Canvas canvasMachines;
    @FXML
    private RadioButton nehRB;
    @FXML
    private RadioButton palmerRB;
    @FXML
    private RadioButton guptdaRB;
    @FXML
    private Slider speedSlider;
    @FXML
    private Label nJobs;
    @FXML
    private Label mMcahines;
    @FXML
    private Button runButton;
    @FXML
    private Canvas canvasSimulation;
    @FXML
    private Canvas canvasSequences;
    @FXML
    private GridPane gridNeh;
    @FXML
    private GridPane gridPalmer;
    @FXML
    private GridPane gridGupta;
    @FXML
    private Label nehMakeSpan;
    @FXML
    private Label palmerMakeSpan;
    @FXML
    private Label guptaMakeSpan;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Rectangle timerLine;
    @FXML
    private Rectangle blockRect;
    @FXML
    private Text timerLabel;
    @FXML
    private Button stopButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Label speedLabel;
    @FXML
    private GridPane jSeq;
    @FXML
    private Label msLabel;
    @FXML
    private Group fileGroup;
    @FXML
    private Group randomGroup;
    @FXML
    private ImageView csvFormat;
    @FXML
    private ImageView txtFormat;
    @FXML
    private ImageView csvSample;
    @FXML
    private ImageView txtSample;
    @FXML
    private ComboBox comboBox;
    @FXML
    private Label labelSelection;
    @FXML
    private RadioButton basedOnJobsRB;
    @FXML
    private RadioButton basedOnMachinesRB;
    @FXML
    private Button computeStatButton;
    @FXML
    private Label formatLabel;
    @FXML
    private Label sampleLabel;
    @FXML
    private StackPane chartPane;
    @FXML
    private TextField dueDateTF;
    @FXML
    private Label nehTardiness;
    @FXML
    private Label palmerTardiness;
    @FXML
    private Label guptaTardiness;
    @FXML
    private Label ddLabel;
    @FXML
    private Label tdLabel;
    @FXML
    private Label statuslabel;

    private LineChart<Number, Number> lineChart1;
    private Task task;
    private ToggleGroup tgFile;
    private ToggleGroup tgAlgorithm;
    private ToggleGroup tgStatistics;
    private String algorithm;
    private int m;
    private int n;
    private int dDate;
    private int selectedMakeSpan;
    private ArrayList<Job> jobs;
    private ArrayList<Job> sequence;
    private Machine[] machines;
    private Timeline timeline;
    private Timeline timeline1;
    private FileChooser fileChooser;
    private BufferedReader reader;
    private Random rand;
    private FlowShopScheduler scheduler;
    private GraphicsContext gcSimulation;
    private GraphicsContext gcMachines;
    private GraphicsContext gcSequenceInfo;
    private static int COUNTER = 0;
    private static int ITERATOR = 0;
    private GridPane[] resultTable;
    private static Integer[] choiceJobs = {20, 50, 100, 200, 500};
    private static Integer[] choiceMachines = {5, 10, 20};
    private String statisticsParameter;
    private int parameterN;
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    private Integer[] category;
    private HashMap<String, HashMap> statisticCategories;
    private HashMap<Integer, ArrayList<Integer[]>> MachinesMakeSpans;
    private HashMap<Integer, ArrayList<Integer[]>> JobsMakeSpans;
    private boolean finnished;

    /**
     * Method openFile
     *
     * @param ActiontEvent event Method untuk membuka file data input file data
     * input dalam bentuk format .txt atau .csv Mengembalikan exception jika
     * file tidak ada
     */
    @FXML
    private void openFile(ActionEvent event) throws FileNotFoundException, IOException {
        Stage mainStage = new Stage();
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Select Data File");
        this.fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files .txt or Workbook .xlsx", "*.txt", "*.xlsx"));
        this.fileChooser.setInitialDirectory(new File("C:\\"));
        File selectedFile = this.fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            loadData(selectedFile);
            this.task = new Task() {
                @Override
                protected Object call() throws Exception {
                    progressIndicator.setVisible(true);
                    scheduler.schedule();
                    scheduler.computeMakeSpan();
                    //scheduler.computeTardiness();
                    Thread.sleep(500);
                    return 1;
                }

                @Override
                protected void succeeded() {
                    System.out.println("succeed");
                    disableControl(false);
                    setSelectedMakeSpanAndSequence(0);
                    nehRB.setSelected(true);
                    progressIndicator.setVisible(false);
                    msLabel.setText(selectedMakeSpan + "");
                    int tard = selectedMakeSpan - dDate;
                    int temp = 0;
                    if (tard > 0) {
                        temp = tard;
                    }
                    tdLabel.setText(temp + "");
                    ddLabel.setText(dDate + "");
                    returnToStart();
                    drawMachines();
                    drawSequences();
                    setTimeLine();
                    showAlert("load");
                }
            };
            new Thread(this.task).start();
        }
    }

    /**
     * Methode generateData
     *
     * @param event Method yang dipanggila ketika tombol generate random data
     * ditekan method akan memanggil method lain untuk mengenerate data
     */
    @FXML
    private void generateData(ActionEvent event) {
        if (this.nJobsTF.getText().trim().isEmpty() || this.mMachinesTF.getText().trim().isEmpty() || this.dueDateTF.getText().trim().isEmpty()) {
            this.showAlert("empty");
            System.out.println("empty");
        } else {
            System.out.println("not empty");
            int n = Integer.parseInt(nJobsTF.getText());
            int m = Integer.parseInt(mMachinesTF.getText());
            int dueDate = Integer.parseInt(dueDateTF.getText());
            this.nJobs.setText(n + "");
            this.mMcahines.setText(m + "");
            if (n >= 2 && m >= 2 && dueDate > 0 && n <= 200 & m <= 20) {
                this.task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        progressIndicator.setVisible(true);
                        generateJobsData(n, m, dueDate);
                        scheduler.schedule();
                        scheduler.computeMakeSpan();
                        //scheduler.computeTardiness();
                        Thread.sleep(500);
                        return 1;
                    }

                    @Override
                    protected void succeeded() {
                        System.out.println("succeeded");
                        disableControl(false);
                        setSelectedMakeSpanAndSequence(0);
                        nehRB.setSelected(true);
                        progressIndicator.setVisible(false);
                        msLabel.setText(selectedMakeSpan + "");
                        int tard = selectedMakeSpan - dDate;
                        int temp = 0;
                        if (tard > 0) {
                            temp = tard;
                        }
                        tdLabel.setText(temp + "");
                        ddLabel.setText(dDate + "");
                        returnToStart();
                        drawMachines();
                        drawSequences();
                        setTimeLine();
                        showAlert("random");
                    }
                };
                new Thread(this.task).start();

            } else {
                showAlert("minimum");
            }
        }
    }

    /**
     * Method initialize Method yang dipanggil ketika perangkat lunak pertama
     * kali dijalankan Method akan mentipkan segala variabel yang dibutuhkan
     * oleh perangkat lunak
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.finnished = false;
        this.statisticsParameter = "jobs";
        this.category = choiceMachines;
        this.comboBox.setItems(FXCollections.observableArrayList(choiceJobs));
        this.randomGroup.setVisible(false);
        this.randomGroup.setDisable(true);
        this.resultTable = new GridPane[3];
        this.resultTable[0] = this.gridNeh;
        this.resultTable[1] = this.gridPalmer;
        this.resultTable[2] = this.gridGupta;
        this.runButton.setTooltip(new Tooltip("Start Simulation"));
        this.stopButton.setTooltip(new Tooltip("Stop Simulation"));
        this.timeline = new Timeline();
        this.timeline1 = new Timeline();
        this.jobs = new ArrayList<Job>();
        rand = new Random();
        this.tgFile = new ToggleGroup();
        this.tgAlgorithm = new ToggleGroup();
        this.tgStatistics = new ToggleGroup();
        this.basedOnJobsRB.setToggleGroup(tgStatistics);
        this.basedOnMachinesRB.setToggleGroup(tgStatistics);
        this.basedOnJobsRB.setSelected(true);
        this.fileRB.setToggleGroup(tgFile);
        this.randomRB.setToggleGroup(tgFile);
        this.fileRB.setSelected(true);
        this.nehRB.setToggleGroup(tgAlgorithm);
        this.palmerRB.setToggleGroup(tgAlgorithm);
        this.guptdaRB.setToggleGroup(tgAlgorithm);
        this.disableControl(true);
        this.algorithm = "neh";
        this.gcMachines = this.canvasMachines.getGraphicsContext2D();
        this.gcSequenceInfo = this.canvasSequences.getGraphicsContext2D();
        this.gcSimulation = this.canvasSimulation.getGraphicsContext2D();

        /**
         * listener untuk mendengarkan input pada textfield jumlah job tab data
         * input textfield hanya akan menerima masukan berupa angka
         */
        this.nJobsTF.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = nJobsTF.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        // if it's not number then just setText to previous one
                        nJobsTF.setText(nJobsTF.getText().substring(0, nJobsTF.getText().length() - 1));
                    }
                }
            }
        });

        /**
         * listener untuk mendengarkan input pada textfield jumlah machine tab
         * data input textfield hanya akan menerima masukan berupa angka
         */
        this.mMachinesTF.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = mMachinesTF.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        // if it's not number then just setText to previous one
                        mMachinesTF.setText(mMachinesTF.getText().substring(0, mMachinesTF.getText().length() - 1));
                    }
                }
            }
        });

        /**
         * listener untuk mendengarkan input pada textfield nilai dueDate pada
         * tab data input textfield hanya akan menerima masukan berupa angka
         */
        this.dueDateTF.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = dueDateTF.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        // if it's not number then just setText to previous one
                        dueDateTF.setText(dueDateTF.getText().substring(0, dueDateTF.getText().length() - 1));
                    }
                }
            }
        });

        /**
         * listener untuk mendengarkan tipe algoritma yang digunakan pada
         * simulasi di tab simulation view listener akan menyesuaikan algoritma
         * yang digunakan pada simulasi sesuai dengan pilihan algoritma pada
         * toggle group radio button
         */
        this.tgAlgorithm.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton rb = (RadioButton) tgAlgorithm.getSelectedToggle();
                if (rb.getText().equalsIgnoreCase("neh")) {
                    returnToStart();
                    algorithm = "neh";

                    sequence = scheduler.getSequence(0);
                    selectedMakeSpan = scheduler.getSelectedMakeSpan(0);
                    msLabel.setText(selectedMakeSpan + "");
                    int tard = selectedMakeSpan - dDate;
                    int temp = 0;
                    if (tard > 0) {
                        temp = tard;
                    }
                    tdLabel.setText(temp + "");
                    setTimeLine();
                    drawMachines();
                    drawSequences();

                } else if (rb.getText().equalsIgnoreCase("palmer")) {
                    returnToStart();
                    algorithm = "palmer";
                    sequence = scheduler.getSequence(1);
                    selectedMakeSpan = scheduler.getSelectedMakeSpan(1);
                    msLabel.setText(selectedMakeSpan + "");
                    int tard = selectedMakeSpan - dDate;
                    int temp = 0;
                    if (tard > 0) {
                        temp = tard;
                    }
                    tdLabel.setText(temp + "");
                    setTimeLine();
                    drawMachines();
                    drawSequences();

                } else {
                    returnToStart();
                    algorithm = "gupta";
                    sequence = scheduler.getSequence(2);
                    selectedMakeSpan = scheduler.getSelectedMakeSpan(2);
                    msLabel.setText(selectedMakeSpan + "");
                    int tard = selectedMakeSpan - dDate;
                    int temp = 0;
                    if (tard > 0) {
                        temp = tard;
                    }
                    tdLabel.setText(temp + "");
                    setTimeLine();
                    drawMachines();
                    drawSequences();

                }
            }
        });

        /**
         * listener untuk memilih tipe input data pada tab data input pilihan
         * data input dapat berupa file ataupun input data random tampilan
         * antarmuka akan berubah sesuai dengan pilihan data input pada toggle
         * group radio button
         */
        this.tgFile.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton rb = (RadioButton) tgFile.getSelectedToggle();
                if (rb.getText().contains("File")) {
                    //nJobsTF.setDisable(true);
                    //mMachinesTF.setDisable(true);
                    //genData.setDisable(true);
                    //openFile.setDisable(false);
                    fileGroup.setVisible(true);
                    fileGroup.setDisable(false);
                    randomGroup.setVisible(false);
                    randomGroup.setDisable(true);
                } else {
                    //nJobsTF.setDisable(false);
                    //mMachinesTF.setDisable(false);
                    //genData.setDisable(false);
                    //openFile.setDisable(true);
                    fileGroup.setVisible(false);
                    fileGroup.setDisable(true);
                    randomGroup.setVisible(true);
                    randomGroup.setDisable(false);
                }
            }
        });

        /**
         * listener untuk memilih parameter pada tab statistics parameter yang
         * dipilih sebagai parameter statistik adalah umlah job atau jumlah
         * machine
         */
        this.tgStatistics.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton rb = (RadioButton) tgStatistics.getSelectedToggle();
                if (rb.getText().contains("Jobs")) {
                    chartPane.getChildren().clear();
                    xAxis = new NumberAxis();
                    yAxis = new NumberAxis();
                    labelSelection.setText("Number Of Jobs");
                    comboBox.getSelectionModel().clearSelection();
                    comboBox.getItems().clear();
                    comboBox.setItems(FXCollections.observableArrayList(choiceJobs));
                    category = choiceMachines;
                    statisticsParameter = "jobs";
                } else {
                    chartPane.getChildren().clear();
                    xAxis = new NumberAxis();
                    yAxis = new NumberAxis();
                    labelSelection.setText("Number Of Machines");
                    comboBox.getSelectionModel().clearSelection();
                    comboBox.getItems().clear();
                    comboBox.setItems(FXCollections.observableArrayList(choiceMachines));
                    category = choiceMachines;
                    statisticsParameter = "machines";
                }
            }
        });
        /**
         * listener untuk menagtur kecepatan jalannya simulasi Kecpetan simulasi
         * berada diantara 1 sampai 60
         */
        this.speedSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //timeline.pause();
                timeline.setRate(speedSlider.getValue());
                timeline1.setRate(speedSlider.getValue());
                speedLabel.setText((int) Math.ceil(speedSlider.getValue()) + "" + "X");
                //timeline.play();
            }
        });
        this.setImageView();
        this.generateStatisticalData();
    }

    /**
     * Method loadData
     *
     * @param dataFile
     * @throws IOException Method loadData digunakan untuk membaca data input
     * alam bentuk file beformat .txt atau .csv Method ini menerima parameter
     * input berupa file Method ini mengenerate job beserta dengan nama,
     * processing time dan due date dari masing-masing job Method ini juga
     * mengenerate machine beserta dengan identitas masing-masing machine
     */
    @FXML
    public void loadData(File dataFile) throws IOException {
        this.jobs.clear();
        int[] proscTime;
        int count = 1;
        if (dataFile.getName().contains("txt") || dataFile.getName().contains("csv")) {
            String[] data;
            reader = new BufferedReader(new FileReader(dataFile));
            String line = reader.readLine();
            //data = line.split(";");
            this.n = Integer.parseInt(line);
            line = reader.readLine();
            //data = line.split(";");
            this.m = Integer.parseInt(line);
            this.setMachines(m);
            line = reader.readLine();
            this.dDate = Integer.parseInt(line);

            this.nJobs.setText(this.n + "");
            this.mMcahines.setText(this.m + "");
            line = reader.readLine();
            while (line != null) {
                data = line.split(",");
                proscTime = new int[m];
                for (int i = 0; i < data.length; i++) {
                    proscTime[i] = Integer.parseInt(data[i]);
                    System.out.print(proscTime[i] + " ");
                }
                System.out.println("");
                this.jobs.add(new Job("Job " + count, proscTime, this.dDate));
                count++;
                line = reader.readLine();
            }
        } else {
            Workbook wb = WorkbookFactory.create(dataFile);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            this.n = (int) cell.getNumericCellValue();
            row = sheet.getRow(1);
            cell = row.getCell(0);
            this.m = (int) cell.getNumericCellValue();
            this.setMachines(m);
            row = sheet.getRow(2);
            cell = row.getCell(0);
            this.dDate = (int) cell.getNumericCellValue();
            int last = sheet.getLastRowNum();
            for (int i = 3; i <= last; i++) {
                row = sheet.getRow(i);
                System.out.println(row.getLastCellNum());
                proscTime = new int[this.m];
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    proscTime[j] = (int) row.getCell(j).getNumericCellValue();
                }
                this.jobs.add(new Job("Job " + count, proscTime, this.dDate));
                count++;
            }
            wb.close();
        }
        
        this.scheduler = new FlowShopScheduler(this.jobs, this.machines);
    }

    /**
     * Method gnerateJobsData
     *
     * @param n
     * @param m
     * @param dDate Method ini menerima parameter msukan berupa jumlah job,
     * jumlah mesin, dan nilai due date Method metdhod ini akan mengenerate job
     * beserta dengan nama, due date, dan processing time yang dibangkitkan
     * secara acak utuk masing-masing job Method ini juga mengenerate machine
     * beserta identitasnya sesuai dengan jumlah machine
     */
    @FXML
    public void generateJobsData(int n, int m, int dDate) {
        this.dDate = dDate;
        setMachines(m);
        this.jobs.clear();
        int[] proscTime;
        int minProscTime = 7;
        int range = 23;
        double temp1 =0;
        double temp2 = 0;
        double temp3 = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("Job" +i+1);
            proscTime = new int[m];
            for (int j = 0; j < m; j++) {
                temp1 = (double)rand.nextInt(23)*Math.random() * 0.9;
                temp2 = (double)rand.nextInt(23)*Math.random() *0.55;
                temp3 = temp1+temp2+minProscTime;
                if((Math.ceil(temp3))-temp3 <0.5){
                    proscTime[j] = (int) Math.ceil(temp3);
                }
                else{
                    proscTime[j] = (int) Math.floor(temp3);
                }
                System.out.print(proscTime[j] + " ");
            }
            System.out.println("");
            this.jobs.add(new Job("Job " + (i + 1), proscTime, this.dDate));
        }
        this.scheduler = new FlowShopScheduler(this.jobs, this.machines);
    }

    /**
     * Method setMachines
     *
     * @param m Method ini digunakan untuk mengenerate machine pada saat
     * menerima data input method ini menerima parameter masukan jumlah machine
     */
    public void setMachines(int m) {
        this.machines = new Machine[m + 1];
        for (int i = 0; i < this.machines.length; i++) {
            this.machines[i] = new Machine("Machine " + (i + 1));
        }
    }

    /**
     * Method runSimulation
     *
     * @param event method ini digunakan untuk menjalankan su=imulasi method ini
     * dipanggil ketika tombol runBtn ditekan untuk menjalankan simulasi
     */
    @FXML
    private void runSimulation(ActionEvent event) {
        if (this.finnished) {
            this.timeline.stop();
            this.timeline1.stop();
            //this.timeline.getKeyFrames().clear();
            this.blockRect.setX(1);
            this.timerLine.setX(0);
            this.timerLabel.setX(0);
            this.timerLabel.setText(0 + "");
            this.finnished = false;
            COUNTER = 0;
        }
        this.timeline.play();
        this.timeline1.play();
        this.statuslabel.setText("Running");
    }

    /**
     * Method showAlert
     *
     * @param parameter Method ini digunakan untuk menampilkan alert dari setiap
     * aksi yang dilakukan oleh user Method ini menerima paramter masukan berupa
     * String Method ini akan mengeluarkan alert box sesuai dengan parameter
     * masukan yang diterima
     */
    @FXML
    private void showAlert(String parameter) {
        this.timeline.stop();
        this.timeline1.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert untuk notifikasi berhasil membaca data input file
        if (parameter.equalsIgnoreCase("load")) {
            alert.setTitle("Data Succesfully Loaded");
            alert.setHeaderText(null);
            alert.setContentText("Successfully loaded data from file with " + this.jobs.size() + " jobs and " + (this.machines.length - 1) + " machines");
            fillTable();
        }
        //alert untuk notifikasi berhasil mengenerate data input random
        if (parameter.equalsIgnoreCase("random")) {
            alert.setTitle("Data Succesfully Generated");
            alert.setHeaderText(null);
            alert.setContentText("Successfully generate data with " + this.jobs.size() + " jobs and " + (this.machines.length - 1) + " machines");
            fillTable();
        }
        //alert untuk notifikasi simulasi selesai
        if (parameter.equalsIgnoreCase("finish")) {
            alert.setTitle("Simulation Finished");
            alert.setHeaderText(null);
            alert.setContentText("Your Simulation has Finished!");
        }
        //alerr untuk notifikasi nilai minimum input pada data input random
        if (parameter.equalsIgnoreCase("minimum")) {
            alert.setTitle("The Input Data Does not Meet The Requirement");
            alert.setHeaderText(null);
            alert.setContentText("minimum required number of jobs (2), number of machines (2) and due date value (>0) & maximum number of jobs (200), number of machines (20)!");
        }
        //alert untuk notifikasi input field yang kosong pada data input random
        if (parameter.equalsIgnoreCase("empty")) {
            alert.setTitle("Input Required");
            alert.setHeaderText(null);
            alert.setContentText("Number of Jobs, Number of Machines, and Due Date Cannot Be Empty!");
        }
        //alert untuk notiikasi input kosong pada tab statistics
        if (parameter.equalsIgnoreCase("empty1")) {
            alert.setTitle("Number of Selected Parameter Required");
            alert.setHeaderText(null);
            alert.setContentText("Number of Selected Parameter is required!");
        }

        alert.show();
    }

    /**
     * method stopSimulation
     *
     * @param event Method ini digunakan untuk menghentikan jalannya simulasi
     * method ini dipanggil ketika tombol stopBtn ditekan
     */
    @FXML
    private void stopSimulation(ActionEvent event) {
        if (!this.finnished && this.statuslabel.getText().equalsIgnoreCase("running") || !this.finnished && this.statuslabel.getText().equalsIgnoreCase("paused")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Stop Simulation");
            alert.setHeaderText(null);
            alert.setContentText("Are You Sure to Terminate Current Simulation Run ?");
            Optional<ButtonType> action = alert.showAndWait();
            //mengembalikan state ke awal simulasi
            if (action.get() == ButtonType.OK) {
                this.finnished = true;
                fillTable();
                this.timeline.stop();
                this.timeline1.stop();
                //this.timeline.getKeyFrames().clear();
                this.blockRect.setX(1);
                this.timerLine.setX(0);
                this.timerLabel.setX(0);
                this.timerLabel.setText(0 + "");
                COUNTER = 0;
                this.statuslabel.setText("Stopped");
            }
        }

    }

    /**
     * Method drawSequence Method ini digunakan untuk menggambar blok-blok
     * processing time untuk model gant chart dari masing-masing job yang sudah
     * dijadwalkan method ini akan menggambar blok-blok processing time dari
     * masing-masing job dengan warna yang ada pada identitas job blok-blok
     * processing time akan diperlihatkan pada saat simulasi dijalankan
     */
    @FXML
    private void drawSequences() {
        double rectHeight = 0;
        double rectWidth = 0;
        int[] machinesTimeLine = new int[this.machines.length];
        int[] proscTime;
        rectHeight = (double) this.canvasSimulation.getHeight() / (double) (this.machines.length - 1);
        rectWidth = (double) this.canvasSimulation.getWidth() / (double) this.selectedMakeSpan;
        Color color;
        for (int i = 0; i < this.sequence.size(); i++) {

            //this.gcSequenceInfo.setFill(Color.BLACK);
            //this.gcSequenceInfo.strokeText(this.sequence.get(i).getName(), i * temp5 + temp5 / 2, 1 * temp6 / 2);
            //this.gcSequenceInfo.setFill(color);
            //this.gcSequenceInfo.fillRect(i * temp5 + temp5 / 2, 1 * temp6 / 2 + 10, 50, 30);
            proscTime = this.sequence.get(i).getProcessingTimes();
            color = this.sequence.get(i).getColor();
            Rectangle rect = new Rectangle();
            rect.setWidth(60);
            rect.setHeight(30);
            rect.setFill(color);
            this.gcSimulation.setFill(color);
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPrefWidth(70);
            this.jSeq.getColumnConstraints().add(i, cc);
            this.jSeq.add(new Text(this.sequence.get(i).getName()), i, 0);
            this.jSeq.add(rect, i, 1);

            for (int j = 0; j < proscTime.length; j++) {
                this.gcSimulation.fillRect(machinesTimeLine[j] * rectWidth, j * rectHeight, proscTime[j] * rectWidth, rectHeight);
                machinesTimeLine[j] += proscTime[j];
                if (machinesTimeLine[j] > machinesTimeLine[j + 1]) {
                    machinesTimeLine[j + 1] = machinesTimeLine[j];
                }
            }
        }
    }

    /**
     * Method drawmachine Method ini digunakan untuk menggambarkan identitas
     * machine pada perangkat lunak simluasi method ini akan menggambarkan list
     * macbine secara vertikal
     */
    @FXML
    private void drawMachines() {
        this.gcMachines.clearRect(this.canvasMachines.getLayoutX(), this.canvasMachines.getLayoutY(), this.canvasMachines.getWidth(), this.canvasMachines.getHeight());
        double temp = (double) this.canvasMachines.getHeight() / (double) (this.machines.length - 1);
        for (int i = 0; i < this.machines.length - 1; i++) {
            this.gcMachines.strokeText(this.machines[i].getName(), 0, i * temp + (temp / 2), 75);
        }
    }

    /**
     * Method stopSimulation Method ini digunakan untuk mengembalikan state
     * perangkat lunak ke stat awal pada saat inisialisasi methode ini dipanggil
     * ketika ada pergantian data input Fungsi dari method ini adalah untuk
     * membersihkan hasil simulasi sebelumnya gar dapat digunakan untuk simulasi
     * baru
     */
    @FXML
    private void returnToStart() {
        this.gcMachines.clearRect(0, 0, this.canvasMachines.getWidth(), this.canvasMachines.getHeight());
        this.gcSimulation.clearRect(0, 0, this.canvasSimulation.getWidth(), this.canvasSimulation.getHeight());
        this.jSeq.getChildren().clear();
        this.timeline.stop();
        this.timeline1.stop();
        //this.timeline.getKeyFrames().clear();
        this.blockRect.setX(1);
        this.timerLine.setX(0);
        this.timerLabel.setX(0);
        this.timerLabel.setText(0 + "");
        this.timeline.getKeyFrames().clear();
        this.timeline1.getKeyFrames().clear();
        this.statuslabel.setText("");
        COUNTER = 0;
    }

    /**
     * Method fillTable Method ini digunakan untuk mengisi tabel pada tab result
     * view dengan hasil simulasi Methode ini mengisi setiap tabel dengan uutan
     * detil dari hasil simulasi
     */
    @FXML
    private void fillTable() {
        this.gridNeh.getChildren().clear();
        this.gridPalmer.getChildren().clear();
        this.gridGupta.getChildren().clear();
        this.fillTableNeh();
        this.fillTablePalmer();
        this.fillTableGupta();
        this.nehMakeSpan.setText(this.scheduler.getSelectedMakeSpan(0) + "");
        this.palmerMakeSpan.setText(this.scheduler.getSelectedMakeSpan(1) + "");
        this.guptaMakeSpan.setText(this.scheduler.getSelectedMakeSpan(2) + "");
        if ((this.scheduler.getSelectedMakeSpan(0) - this.dDate) < 0) {
            this.nehTardiness.setText(0 + "");
        }
        if ((this.scheduler.getSelectedMakeSpan(1) - this.dDate) < 0) {
            this.palmerTardiness.setText(0 + "");
        }
        if ((this.scheduler.getSelectedMakeSpan(2) - this.dDate) < 0) {
            this.guptaTardiness.setText(0 + "");
        }
        if ((this.scheduler.getSelectedMakeSpan(0) - this.dDate) >= 0) {
            this.nehTardiness.setText((this.scheduler.getSelectedMakeSpan(0) - this.dDate) + "");
        }
        if ((this.scheduler.getSelectedMakeSpan(1) - this.dDate) >= 0) {
            this.palmerTardiness.setText((this.scheduler.getSelectedMakeSpan(1) - this.dDate) + "");
        }
        if ((this.scheduler.getSelectedMakeSpan(2) - this.dDate) >= 0) {
            this.guptaTardiness.setText((this.scheduler.getSelectedMakeSpan(2) - this.dDate) + "");
        }

    }

    /**
     * Method fillTableNeh Methode yang dipanggil ketika method fillTable
     * dipanggil Method ini berfungsi untuk mengisi tabel hasil penjadwalan
     * dengan algoritma NEH Isi dari tabel ini adalah urutan job yang sudah
     * dijadwalkan beserta processing time untuk tiap machine
     */
    @FXML
    private void fillTableNeh() {
        ArrayList<Job> temp = new ArrayList<Job>();
        temp = this.scheduler.getSequence(0);
        for (int i = 0; i < this.scheduler.getMachines().length - 1; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPrefWidth(70);
            //RowConstraints rc = new RowConstraints();
            //this.gridNeh.getRowConstraints().add(i+1,rc);
            this.gridNeh.getColumnConstraints().add(i + 1, cc);
            this.gridNeh.addColumn(i + 1, new Text("Machine " + (i + 1)));
            //this.gridNeh.add(, i + 1, 0);
        }
        for (int i = 0; i < temp.size(); i++) {
            this.gridNeh.add(new Text(temp.get(i).getName()), 0, i + 1);
            int[] tempJobs = temp.get(i).getProcessingTimes();
            for (int j = 0; j < tempJobs.length; j++) {
                this.gridNeh.add(new Text(tempJobs[j] + ""), (j + 1), (i + 1));
            }
        }
    }

    /**
     * Method fillTableNeh Methode yang dipanggil ketika method fillTable
     * dipanggil Method ini berfungsi untuk mengisi tabel hasil penjadwalan
     * dengan algoritma Palmer Isi dari tabel ini adalah urutan job yang sudah
     * dijadwalkan beserta processing time untuk tiap machine
     */
    @FXML
    private void fillTablePalmer() {
        ArrayList<Job> temp = new ArrayList<Job>();
        temp = this.scheduler.getSequence(1);
        for (int i = 0; i < this.scheduler.getMachines().length - 1; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPrefWidth(70);
            this.gridPalmer.getColumnConstraints().add(i + 1, cc);
            this.gridPalmer.add(new Text("Machine " + (i + 1)), i + 1, 0);
        }
        for (int i = 0; i < temp.size(); i++) {
            this.gridPalmer.add(new Text(temp.get(i).getName()), 0, i + 1);
            int[] rectHeight = temp.get(i).getProcessingTimes();
            for (int j = 0; j < rectHeight.length; j++) {
                this.gridPalmer.add(new Text(rectHeight[j] + ""), (j + 1), (i + 1));
            }
        }
    }

    /**
     * Method fillTableNeh Methode yang dipanggil ketika method fillTable
     * dipanggil Method ini berfungsi untuk mengisi tabel hasil penjadwalan
     * dengan algoritma Gupta Isi dari tabel ini adalah urutan job yang sudah
     * dijadwalkan beserta processing time untuk tiap machine
     */
    @FXML
    private void fillTableGupta() {
        ArrayList<Job> temp = new ArrayList<Job>();
        temp = this.scheduler.getSequence(2);
        for (int i = 0; i < this.scheduler.getMachines().length - 1; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPrefWidth(70);
            this.gridGupta.getColumnConstraints().add(i + 1, cc);
            this.gridGupta.add(new Text("Machine " + (i + 1)), i + 1, 0);
        }
        for (int i = 0; i < temp.size(); i++) {
            this.gridGupta.add(new Text(temp.get(i).getName()), 0, i + 1);
            int[] rectHeight = temp.get(i).getProcessingTimes();
            for (int j = 0; j < rectHeight.length; j++) {
                this.gridGupta.add(new Text(rectHeight[j] + ""), (j + 1), (i + 1));
            }
        }
    }

    /**
     * Method pauseSimulation
     *
     * @param event Method ini dipanggil ketika tombol pause pada antarmuka
     * simulasi ditekan Fungsi dari method ini adalah untuk menghentikan
     * jalannya simulasi untuk sementara waktu
     */
    @FXML
    private void pauseSimulation(ActionEvent event) {
        if (!this.finnished && this.statuslabel.getText().equalsIgnoreCase("running")) {
            this.timeline.pause();
            this.timeline1.pause();
            this.statuslabel.setText("Paused");
        }
    }

    /**
     * Method setSelectedMakeSpanAndSequence
     *
     * @param index Method ini berfungsi untuk mengatur isi dari label make span
     * pada antarmuka perangkat lunak Method ini akan dipanggil setiap kali
     * terjadi perubahan seleksi dari algoritma yang digunakan pada simulasi
     * Method ini menerima paraemter masukan berupa index bertipe int angka yang
     * dimasukan berada pada kisaran 0-2 0 = NEH, 1= Palmer, 2 = Gupta
     */
    private void setSelectedMakeSpanAndSequence(int index) {
        this.sequence = this.scheduler.getSequence(index);
        this.selectedMakeSpan = this.scheduler.getSelectedMakeSpan(index);
    }

    /**
     * Method setTimeLine Method ini digunakan untuk mengatur timeline untuk
     * simulasi timeline diatur berdasarkan total make span dari hasil algoritma
     * penjadwalan yang dipilih pada seleksi algoritma Method ini berfungsi
     * untuk mengatur waktu jalannya simulasi
     */
    private void setTimeLine() {
        double move = (double) this.selectedMakeSpan / (double) this.canvasSimulation.getWidth();
        KeyValue timeLiner = new KeyValue(this.timerLine.xProperty(), this.canvasSimulation.getWidth());
        KeyValue blockLiner = new KeyValue(this.blockRect.xProperty(), this.canvasSimulation.getWidth());
        KeyValue timeLabel = new KeyValue(this.timerLabel.xProperty(), this.canvasSimulation.getWidth());
        EventHandler counter = new EventHandler() {
            @Override
            public void handle(Event event) {
                COUNTER++;
                timerLabel.setText(COUNTER + "");
                if (COUNTER == selectedMakeSpan) {
                    showAlert("finish");
                    finnished = true;
                    statuslabel.setText("Finnished");
                }
            }
        };
        this.timeline1.setCycleCount(this.selectedMakeSpan);
        this.timeline.setCycleCount(1);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(this.selectedMakeSpan), timeLiner, blockLiner, timeLabel);
        KeyFrame counterLabel = new KeyFrame(Duration.seconds(1), counter);
        this.timeline.getKeyFrames().addAll(keyFrame);
        this.timeline1.getKeyFrames().add(counterLabel);

    }

    /**
     * Method setImageView Method ini berfungsi untuk mengganti gambar contoh
     * dan sample pada tab data input Method ini akan mengganti gambar contoh
     * dan sample file dengan interval 5 detik
     */
    @FXML
    private void setImageView() {
        Timeline timeImage = new Timeline();
        EventHandler counter = new EventHandler() {
            @Override
            public void handle(Event event) {
                ITERATOR++;
                if (ITERATOR % 2 == 0) {
                    csvFormat.setVisible(true);
                    csvSample.setVisible(true);
                    txtFormat.setVisible(false);
                    txtSample.setVisible(false);
                    formatLabel.setText("XLSX File Format");
                    sampleLabel.setText("XLSX Sample File");
                } else {
                    csvFormat.setVisible(false);
                    csvSample.setVisible(false);
                    txtFormat.setVisible(true);
                    txtSample.setVisible(true);
                    formatLabel.setText("TXT File Format");
                    sampleLabel.setText("TXT Sample File");
                }
            }
        };
        KeyFrame counterImage = new KeyFrame(Duration.seconds(5), counter);
        timeImage.setCycleCount(Timeline.INDEFINITE);
        timeImage.getKeyFrames().add(counterImage);
        timeImage.play();
    }

    /**
     * Method showStatistics
     *
     * @param event Method ini berfungsi untuk menampilkan statistik total make
     * span berdasarkan parameter uji dari algoritma penjadwalan NEH, Palmer,
     * dan Gupta MEthod ini dipanggil ketika tombol show ditekan
     */
    @FXML
    private void showStatistics(ActionEvent event) {
        if (this.comboBox.getSelectionModel().isEmpty()) {
            this.showAlert("empty1");
        } else {
            parameterN = (int) comboBox.getSelectionModel().getSelectedItem();
            this.lineChart1 = new LineChart<Number, Number>(this.xAxis, this.yAxis);
            this.lineChart1.setTitle("Flow Shop Scheduling Algorithm Statistics");

            this.yAxis.setLabel("Total Make Span");
            XYChart.Series series1 = new XYChart.Series();
            series1.setName("NEH");
            XYChart.Series series2 = new XYChart.Series();
            series2.setName("Palmer");
            XYChart.Series series3 = new XYChart.Series();
            series3.setName("Gupta");
            Integer[] makespans;
            ArrayList<Integer[]> result = getStatisticalMakeSpan();
            if (this.statisticsParameter.equalsIgnoreCase("jobs")) {  
                this.xAxis.setLabel("");
                this.xAxis.setLabel("Number Of Machines");
                this.xAxis.setLowerBound(0);
                this.xAxis.setUpperBound(25);
                this.xAxis.setTickUnit(5);
                this.xAxis.setAutoRanging(false);
                for (int i = 0; i < result.size(); i++) {
                    makespans = result.get(i);
                    series1.getData().add(new XYChart.Data(choiceMachines[i], makespans[0]));
                    series2.getData().add(new XYChart.Data(choiceMachines[i], makespans[1]));
                    series3.getData().add(new XYChart.Data(choiceMachines[i], makespans[2]));
                }
            } else {
                this.xAxis.setLabel("");
                this.xAxis.setLabel("Number Of Jobs");
                this.xAxis.setLowerBound(0);
                this.xAxis.setUpperBound(525);
                this.xAxis.setTickUnit(50);
                this.xAxis.setAutoRanging(false);
                for (int i = 0; i < result.size(); i++) {
                    makespans = result.get(i);
                    series1.getData().add(new XYChart.Data(choiceJobs[i], makespans[0]));
                    series2.getData().add(new XYChart.Data(choiceJobs[i], makespans[1]));
                    series3.getData().add(new XYChart.Data(choiceJobs[i], makespans[2]));
                }
            }
            this.chartPane.getChildren().add(this.lineChart1);
            this.lineChart1.getData().setAll(series1, series2, series3);
            this.lineChart1.setAnimated(true);
        }
    }

    /**
     * Method getStatisticalMakeSpan
     *
     * @return ArrayList method ini berfungsi untuk mengembalikan nilai make
     * span dari parameter uji Method ini mengembalikan nilai make span dalam
     * bentuk ArrayList
     */
    private ArrayList getStatisticalMakeSpan() {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        HashMap<Integer, ArrayList<Integer[]>> hash = this.statisticCategories.get(this.statisticsParameter);
        result = hash.get(this.parameterN);
        return result;
    }

    /**
     * Method generateStatisticalData Method ini berfungsi untuk membangkitkan
     * data statistik nilai make span hasil dari algoritma penjadwalan flow shop
     * Method ini dipanggil ketika inisialisasi perangkat lunak data nilai make
     * span yang dibangkitkan merupakan data hasil pengujian perangkat lunak
     */
    private void generateStatisticalData() {
        this.statisticCategories = new HashMap<String, HashMap>();
        this.MachinesMakeSpans = new HashMap<Integer, ArrayList<Integer[]>>();
        this.JobsMakeSpans = new HashMap<Integer, ArrayList<Integer[]>>();
        ArrayList<Integer[]> listMachine = new ArrayList<Integer[]>();
        ArrayList<Integer[]> listJobs = new ArrayList<Integer[]>();
        Integer[] data11 = {1286, 1384, 1425};
        Integer[] data12 = {2733, 2774, 2820};
        Integer[] data13 = {5519, 5749, 5765};
        Integer[] data14 = {10518, 10582, 11202};
        Integer[] data15 = {25289, 25350, 26338};
        Integer[] data21 = {1680, 1940, 2027};
        Integer[] data22 = {3135, 3478, 3672};
        Integer[] data23 = {5846, 6315, 6514};
        Integer[] data24 = {10942, 11638, 12201};
        Integer[] data25 = {25468, 25895, 27800};
        Integer[] data31 = {2410, 2451, 2852};
        Integer[] data32 = {4082, 4848, 4706};
        Integer[] data33 = {6541, 7298, 7741};
        Integer[] data34 = {11595, 14583, 13671};
        Integer[] data35 = {26670, 28227, 29835};
        listMachine.add(data11);
        listMachine.add(data12);
        listMachine.add(data13);
        listMachine.add(data14);
        listMachine.add(data15);
        MachinesMakeSpans.put(5, listMachine);
        listMachine = new ArrayList<Integer[]>();
        listMachine.add(data21);
        listMachine.add(data22);
        listMachine.add(data23);
        listMachine.add(data24);
        listMachine.add(data25);
        MachinesMakeSpans.put(10, listMachine);
        listMachine = new ArrayList<Integer[]>();
        listMachine.add(data31);
        listMachine.add(data32);
        listMachine.add(data33);
        listMachine.add(data34);
        listMachine.add(data35);
        MachinesMakeSpans.put(20, listMachine);
        listJobs.add(data11);
        listJobs.add(data21);
        listJobs.add(data31);
        JobsMakeSpans.put(20, listJobs);
        listJobs = new ArrayList<Integer[]>();
        listJobs.add(data12);
        listJobs.add(data22);
        listJobs.add(data32);
        JobsMakeSpans.put(50, listJobs);
        listJobs = new ArrayList<Integer[]>();
        listJobs.add(data13);
        listJobs.add(data23);
        listJobs.add(data33);
        JobsMakeSpans.put(100, listJobs);
        listJobs = new ArrayList<Integer[]>();
        listJobs.add(data14);
        listJobs.add(data24);
        listJobs.add(data34);
        JobsMakeSpans.put(200, listJobs);
        listJobs = new ArrayList<Integer[]>();
        listJobs.add(data15);
        listJobs.add(data25);
        listJobs.add(data35);
        JobsMakeSpans.put(500, listJobs);
        this.statisticCategories.put("jobs", JobsMakeSpans);
        this.statisticCategories.put("machines", MachinesMakeSpans);
    }

    /**
     * method disbaleControl
     *
     * @param param Method ini digunakan untuk mengatur fungsi kontrol pada
     * antarmuka tab simulation view Method ini menerima masukan bertipe boolean
     * jika masukan true, maka seluruh kontrol pada simualtion view akan
     * dinonaktifkan jika masukan false, maka seluruh kontrol pada simulation
     * view akan diaktifkan
     */
    @FXML
    private void disableControl(boolean param) {
        this.nehRB.setDisable(param);
        this.palmerRB.setDisable(param);
        this.guptdaRB.setDisable(param);
        this.runButton.setDisable(param);
        this.pauseButton.setDisable(param);
        this.stopButton.setDisable(param);
        this.speedSlider.setDisable(param);
    }
}
