/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lauri Puoskari
 */
public class WeatherDataTab extends DataTab {
    private TreeView checkBoxTree;
    
    WeatherDataTab(MainView mainView) {
        super(mainView);
        this.setText("Weather data");
        GridPane gridPane = (GridPane) super.getContent();
        
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        gridPane.add(this.checkBoxTree, 2, 1, 1, 2);
        
        CheckBoxTreePopulator cbtPopulator = new CheckBoxTreePopulator();
        cbtPopulator.populateCheckBoxTree(this.checkBoxTree, this.getText());
    }
        
    @Override
    TreeItem getCbTreeRoot() {
        return this.checkBoxTree.getRoot();
    }
}
