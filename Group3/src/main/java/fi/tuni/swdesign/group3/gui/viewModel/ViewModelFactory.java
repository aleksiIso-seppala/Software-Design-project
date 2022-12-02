package fi.tuni.swdesign.group3.gui.viewModel;

import fi.tuni.swdesign.group3.gui.model.ModelFactory;

/**
 * Factory class for viewModel
 * @author jukka
 */
public class ViewModelFactory {
    private final ViewModel viewModel;
    
    /**
     * Default constructor for ViewModelFactory
     * @param mf Modelfactory to reference to viewModel
     */
    public ViewModelFactory(ModelFactory mf){
        this.viewModel = new ViewModel(mf.getModel());
    }
    
    /**
     * Getter for the viewModel
     * @return viewModel
     */
    public ViewModel getViewModel(){
        return this.viewModel;
    }
}
