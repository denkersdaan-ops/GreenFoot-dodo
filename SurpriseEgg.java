import greenfoot.*;

import java.util.List;
import java.util.ArrayList;
/**
 * 
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 01-01-2017
 */
public class SurpriseEgg extends Egg
{
    private static int MAX_VALUE = 1000;
    
    private List<SurpriseEgg> generatedEggs;

    public SurpriseEgg () {
        super( Greenfoot.getRandomNumber( MAX_VALUE ) + 1 );
    }  
    
    /*
     * starts the generation of the SurpiseEggs and saves the list.
     * <p> it also gives the list of eggs to the eggs it generated.
     */
    
    public void generateListOfSurpriseEggs(int size){
        generatedEggs = generateSurpriseEggs(size);
        for(SurpriseEgg egg : generatedEggs){
           egg.generatedEggs = this.generatedEggs;
        }
    }
    
     /*
     * starts the generation of the SurpiseEggs and saves the list.
     * <p> it also gives the list of eggs to the eggs it generated.
     */
    
    public void generateListOfSurpriseEggs(int size, World world ){
        generatedEggs = generateSurpriseEggs(size, world);
        for(SurpriseEgg egg : generatedEggs){
           egg.generatedEggs = this.generatedEggs;
        }
    }
        
    private List<SurpriseEgg> generateSurpriseEggs( int size, World world ) {
        int emptyCells = world.getHeight() * world.getWidth() - world.numberOfObjects();
        if ( size <= emptyCells ) {
            List<SurpriseEgg> generatedEggs = new ArrayList<SurpriseEgg> ( size );
            int eggNr = 0;
            while ( eggNr < size ) {
                SurpriseEgg newEgg = new SurpriseEgg ();
                placeEgg( newEgg,  Greenfoot.getRandomNumber( emptyCells - eggNr ), world );
                generatedEggs.add( newEgg );
                eggNr++;
            }
            return generatedEggs;
        } else {
            showError( "Too many surprise eggs ordered", world );
            return new ArrayList<SurpriseEgg>();
        }
    }
        
    private List<SurpriseEgg> generateSurpriseEggs( int size) {
        
        World world = getWorld();
        
        int emptyCells = world.getHeight() * world.getWidth() - world.numberOfObjects();
        if ( size <= emptyCells ) {
            List<SurpriseEgg> generatedEggs = new ArrayList<SurpriseEgg> ( size );
            int eggNr = 0;
            while ( eggNr < size ) {
                SurpriseEgg newEgg = new SurpriseEgg ();
                placeEgg( newEgg,  Greenfoot.getRandomNumber( emptyCells - eggNr ), world );
                generatedEggs.add( newEgg );
                eggNr++;
            }
            return generatedEggs;
        } else {
            showError( "Too many surprise eggs ordered", world );
            return new ArrayList<SurpriseEgg>();
        }
    }
    
    private void placeEgg( Egg egg, int pos, World world ) {
        for( int y = 0; y < world.getHeight(); y++){
            for (int x = 0; x < world.getWidth(); x++){
                if ( world.getObjectsAt(x, y, null).size() == 0 ) {
                    if ( pos == 0 ) {
                        world.addObject( egg, x, y);
                    } else {
                        pos--;
                    }
                }
            }
        }
    }
    private void showError ( String err_msg, World world ) {
        Message.showMessage(  new Alert( err_msg ), world );
    }
    
    public void printCoordinatesOfEgg(Egg egg){
        System.out.println(egg.getX() + " : " + egg.getY());
    }
    
    public void printAllSurpriseEggsCoordinates(){
        if(generatedEggs != null){
            for(Egg egg : generatedEggs){
                if(egg != null){
                    printCoordinatesOfEgg(egg);
                }
            }
        }else{
            System.out.println("there are no eggs");
        }
    }

}
