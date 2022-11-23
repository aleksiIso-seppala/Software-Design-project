package fi.tuni.swdesign.group3.view;

/**
 *
 * @author jukka
 * Factory for Models
 */
public class ModelFactory {
    private Model model;

    /**
     * Default Constructor for ModelFactory
     */
    ModelFactory(){        
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
