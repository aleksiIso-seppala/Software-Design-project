package fi.tuni.swdesign.group3.view;

/**
 *
 * @author jukka
 * Factory class for viewModel
 */
public class ViewModelFactory {
    private final ViewModel viewModel;
    
    /**
     * Default constructor for ViewModelFactory
     * @param mf Modelfactory to reference to viewModel
     */
    ViewModelFactory(ModelFactory mf){
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
