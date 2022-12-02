package fi.tuni.swdesign.group3.gui.model;

/**
 * Factory for Model classes
 * @author jukka
 * 
 */
public class ModelFactory {
    private Model model;

    /**
     * Default Constructor for ModelFactory
     */
    public ModelFactory(){        
    }
    
    /**
     * Getter for model with lazy constructor
     * @return Model class
     */
    public Model getModel(){
        if(this.model == null){
            this.model = new Model();
        }
        return this.model;
    }
}
