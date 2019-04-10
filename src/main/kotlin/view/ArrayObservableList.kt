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
