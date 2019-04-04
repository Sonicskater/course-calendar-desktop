package view

import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException
import java.net.URL
import java.util.*

import javafx.application.Platform.runLater
import javafx.collections.ModifiableObservableListBase
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import jfxtras.styles.jmetro8.JMetro
import model.database.DBUniqueID
import model.database.DataBase
import model.database.EDBTypeCode
import model.database.IDTypeMismatchExcception
import model.types.Course
import model.types.Department
import model.types.Program
import java.util.ArrayList
import javax.swing.plaf.multi.MultiTabbedPaneUI


class UserViewNew : Initializable {
    var userType: Int = 0
        set(value){
            field = value
            if(field == 1){
                adminToolbar.isDisable = false
            }else{
                adminToolbar.isDisable = true
            }

        }


    //JavaFX UI Elements
    @FXML
    private lateinit var tabbedPaneUI: TabPane

    @FXML
    private lateinit var depTable : TableView<Department>

    @FXML
    private lateinit var progTable : TableView<Program>

    @FXML
    private lateinit var optCourseTable : TableView<Course>

    @FXML
    private lateinit var reqCourseTable : TableView<Course>

    @FXML
    private lateinit var depName : TableColumn<Department, String>

    @FXML
    private lateinit var numProg : TableColumn<Department, Int>

    @FXML
    private lateinit var progName: TableColumn<Program, String>

    @FXML
    private lateinit var progDesc: TableColumn<Program, String>

    @FXML
    private lateinit var delete: Button

    @FXML
    private lateinit var add: Button

    @FXML
    private lateinit var adminToolbar: ToolBar

    @FXML
    private lateinit var root: AnchorPane

    private var selectedDepartment: Department? = null

    private var selectedProgram: Program? = null


    override fun initialize(location: URL?, resources: ResourceBundle?) {


        //Configure tables to desired settings at startup
        runLater(Runnable {
            depTable.requestFocus()
            depTable.getSelectionModel().select(0)
            depTable.getFocusModel().focus(0)
        })

        runLater(Runnable {
            progTable.requestFocus()
            progTable.getSelectionModel().select(0)
            progTable.getFocusModel().focus(0)
        })

        runLater(Runnable {
            optCourseTable.requestFocus()
            optCourseTable.getSelectionModel().select(0)
            optCourseTable.getFocusModel().focus(0)
        })

        runLater(Runnable {
            reqCourseTable.requestFocus()
            reqCourseTable.getSelectionModel().select(0)
            reqCourseTable.getFocusModel().focus(0)
        })


        //Configure Buttons

        delete.onAction = EventHandler {
            delete()
        }

        add.onAction = EventHandler {
            add()
        }

        //Setup TabChange event
        tabbedPaneUI.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
            when(newValue){
                0 -> updateDepartmentList()
                1 -> updateProgramList()

            }
        }


        //Define cellfactorys for rendering cells from objects
        depName.cellValueFactory = PropertyValueFactory("name")
        numProg.cellValueFactory = PropertyValueFactory("progNum")
        progName.cellValueFactory = PropertyValueFactory("name")
        progDesc.cellValueFactory = PropertyValueFactory("description")

        //configure cell with text wrapping
        progDesc.setCellFactory{
            val cell = TableCell<Program, String>()
            val text = Text()
            cell.setGraphic(text)
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE)
            text.wrappingWidthProperty().bind(progDesc.widthProperty())
            text.textProperty().bind(cell.itemProperty())
            cell
        }


        //Configure table row selection events
        depTable.selectionModel.selectedItemProperty().addListener { _, _, newValue ->

            selectedDepartment = newValue

            updateProgramList()
        }
        progTable.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            selectedProgram = newValue

        }



        //Update department list for initial viewing
        updateDepartmentList()

        //Apply 3rd-party theme
        JMetro(JMetro.Style.LIGHT).applyTheme(root)



    }

    //Get required courses and parse to object list
    private fun updateRequiredList(program: Program) {
        val courseList = ArrayObservableList<Course>()

        for (id: DBUniqueID in program.getRequired()) {
            courseList.add(DataBase.getCourse(id))
        }

        reqCourseTable.items = courseList
    }

    //Get optional courses and parse to object list
    private fun updateOptionalList(program: Program) {
        val courseList = ArrayObservableList<Course>()

        for (id: DBUniqueID in program.getOptional()) {
            courseList.add(DataBase.getCourse(id))
        }

        optCourseTable.items = courseList
    }

    //Get programs and parse to object list
    private fun updateProgramList() {
        val progList = ArrayObservableList<Program>()
        if(selectedDepartment == null){
            progTable.items = progList
            return
        }else {
            selectedDepartment = DataBase.getDepartment(selectedDepartment!!.id)
            for (id: DBUniqueID in selectedDepartment!!.getPrograms()) {
                progList.add(DataBase.getProgram(id))
            }
        }

        progTable.items = progList
        progTable.selectionModel.selectFirst()
        //updateRequiredList()
        //updateOptionalList()
    }

    //Get departments and parse to object list
    private fun updateDepartmentList() {
        val deptList = ArrayObservableList<Department>()

        for (id: DBUniqueID in DataBase.getDepartmentIDs()) {
            deptList.add(DataBase.getDepartment(id))
        }

        depTable.items = deptList
        depTable.selectionModel.selectFirst()
    }


    //Unused logout call, just close window
    @Throws(IOException::class)
    fun logOut(event: ActionEvent) {
        val parent = FXMLLoader.load<Parent>(javaClass.getResource("interface/loginView.fxml"))
        val newScene = Scene(parent)
        val loginStage = (event.source as Node).scene.window as Stage
        loginStage.scene = newScene
        loginStage.show()
    }

    //Delete currently selected item
    fun delete(){
        when(tabbedPaneUI.selectionModel.selectedIndex){
            0 -> {
                selectedDepartment!!.delete()
                updateDepartmentList()

            }
            1 -> {
                selectedProgram!!.delete()
                updateProgramList()

            }
        }
    }

    //Add item [currently default filler item] to current list, and make relationships accordingly
    fun add(){
        when(tabbedPaneUI.selectionModel.selectedIndex){
            0 -> {
                var d: Department? = null
                try {
                    d = Department(DBUniqueID(EDBTypeCode.DEPARTMENT))
                } catch (idTypeMismatchExcception: IDTypeMismatchExcception) {
                    idTypeMismatchExcception.printStackTrace()
                }

                d!!.name = "Computer Science"
                d.save()
                updateDepartmentList()

            }
            1 -> {
                if (selectedDepartment != null){
                    var p: Program? = null
                    try {
                        p = Program(DBUniqueID(EDBTypeCode.PROGRAM))
                    } catch (idTypeMismatchExcception: IDTypeMismatchExcception) {
                        idTypeMismatchExcception.printStackTrace()
                    }

                    p!!.name = "Bachelor of Computer Science"
                    p.description = "A 4-year program that teaches general computer science"
                    p.save()
                    selectedDepartment!!.addProgram(p.id)
                    selectedDepartment!!.save()
                    updateProgramList()
                }

            }
        }
    }

    //unimplemented, will open modify interface and apply changes when it closes.
    fun modify(){

    }
}

//ArrayList that implements ObservableList interface via ModifiableObservableListBase abstract class
//Updates javafx view when contents change.
open class ArrayObservableList<E> : ModifiableObservableListBase<E>(){

    private val delegate = ArrayList<E>()

    override fun get(index: Int): E {
        return delegate[index]
    }

    override val size: Int
        get() = delegate.size

    override fun doAdd(index: Int, element: E) {
        delegate.add(index, element)
    }

    override fun doSet(index: Int, element: E): E {
        return delegate.set(index, element)
    }

    override fun doRemove(index: Int): E {
        return delegate.removeAt(index)
    }
}
