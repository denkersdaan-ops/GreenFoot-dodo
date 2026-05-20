import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {  
       
    }


    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }
    
    /**
     * Moves one cell backword from current direction
     */
    public void OneCellBackwards(){
        turnAround();
        move();
        turnAround();
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead()){
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Moves around ONE fence
     * 
     * <P> if it runs into a fence while climbing over one it will brake it is made for ONE fence
     */
    public void climbOverFence(){
        turnLeft();
        move();
        turnRight();
        jump(2);
        turnRight();
        move();
        turnLeft();
    }
    
    
    /**
     * checks if there is a grain infront of it
     */
    public boolean grainAhead(){
        move();
        if(onGrain()){
            OneCellBackwards();
            return true;
        }else{
            OneCellBackwards();
            return false;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            System.out.println("moved "+ nrStepsTaken);
        }
    }
    
     /**
     * look at all cells add the sum of the eggs per row to the total
     */
    public void scanWorldForAllEggs(){
        int startX = getX();
        int startY = getY();
        
        int totalEggs = 0;
        
        goToLocation(0, 0);
        setDirection(1);
        
        int row = 0;
        
        while(row <= getWorld().getHeight() - 2){
            totalEggs += countEggsInRow();
            row++;
            turnRight();
            move();
            turnLeft();
        }
        
        totalEggs += countEggsInRow();
        
        showCompliment("total eggs: " + totalEggs);
        
         goToLocation(startX, startY);
        
    }
    
    /**
     * scans the word for the row with the most eggs.
     * 
     * <p> if the most amount of rows has the same ammount it wil grab the first or last debending on findFirst
     *
     * @param  boolean findFirst: true is grab the first best, false is grab the lest best
     */
    
    public void scanWorldForBestRow(boolean findFirst){
        int startX = getX();
        int startY = getY();
        
        int bestRow = 0;
        int bestEggs = 0;
        
        goToLocation(0, 0);
        setDirection(1);
        
        int row = 0;
        
        while(row <= getWorld().getHeight() - 2){
            if(findFirst){
                if(bestEggs < countEggsInRow()){
                    bestEggs = countEggsInRow();
                    bestRow = row;
                }
            }else{
                if(bestEggs <= countEggsInRow()){
                    bestEggs = countEggsInRow();
                    bestRow = row;
                } 
            }
            row++;
            turnRight();
            move();
            turnLeft();
        }
        
        if(findFirst){
                if(bestEggs < countEggsInRow()){
                    bestEggs = countEggsInRow();
                    bestRow = row;
                }
            }else{
                if(bestEggs <= countEggsInRow()){
                    bestEggs = countEggsInRow();
                    bestRow = row;
                } 
            }
        
        showCompliment("best ammout: " + bestEggs + " at row: " + bestRow);
        
         goToLocation(startX, startY);
        
    }

      /**
     * Move given number of cells forward in the current direction and lay eggs on the cells.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int n: the number of steps made
     */
    public void layTrailOfEggs( int n ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < n ) { // check if more steps must be taken  
            move();                         // take a step
            if(canLayEgg()){
                layEgg();
            }
            nrStepsTaken++;                 // increment the counter
        }
    }
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( ! borderAhead() ){
            // print coordinates
            System.out.println("x:" + getX() + " y:" + getY());
            move();
        }
    }
    
     /**
     * Walks to edge of the world in front of it
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *
     */
    
      public void walkToWorldEdge( ){
        while( ! borderAhead() ){
            move();
        }
    }
    
    /**
     * Walks to edge of the world behind it
     * 
     * <p> Initial: Dodo is on East side of world facing East.
     * <p> Final:   Dodo is on West side of world facing East.
     *
     */
    public void goBackToStartOfRowAndFaceBack(){
        turnAround();
        walkToWorldEdge();
        turnAround();
    }
    
    /**
     * Walks to edge of the world in front of it walking around fences
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     * 
     * <p> running into a fence wil start climbOverFence() with the catch of it braking if running into a fence while cimbing over one.
     *
     */
    public void walkToWorldEdgeClimbingOverFences(){
         while( ! borderAhead() ){
            if(fenceAhead()){
                climbOverFence();
            }
            move();
        }
    }
    
    /**
     * Walks to edge of the world in front of it walking around fences stops at nest
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     * 
     * <p> running into a fence wil start climbOverFence() with the catch of it braking if running into a fence while cimbing over one.
     * 
     * <p> same a walkToWorldEdgeClimbingOverFences() but stops at nest.
     *
     */
    public void walkToNestClimbingOverFences(){
         while( ! onNest() && ! borderAhead()){
            if(fenceAhead()){
                climbOverFence();
            }
             if(onNest() && canLayEgg()){
                layEgg();
                break;
            }
        
            move();
        }
        
        if(onNest() && canLayEgg()){
            layEgg();
        }
    }
    
     /**
     * Walks to edge of the world printing the coordinates at each step and pick up all the grain
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     *              all grain found on the way is picked up.
     */
    
    public void pickUpGrainAndPrintCoordinates(){
        while( ! borderAhead() ){
            // print coordinates
            if(onGrain()){
                pickUpGrain();
            }
            
            System.out.println("x:" + getX() + " y:" + getY());
            move();
        }
    }
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              all nest it ran into have an egg in it.
     */
    
    public void walkToWorldEdgeNoEmptyNest(){
        //if u start on a nest lay an egg 
        if(onNest() && canLayEgg()){
                layEgg();
        }
        while(! borderAhead()){
            move();
            if(onNest() && canLayEgg()){
                layEgg();
            }
        }
    }
    
    /**
     * Walks to edge of the world count egg and go back
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              count all eggs it ran into and shows it.
     */
    
    public int countEggsInRow(){
        int eggCount = 0;
        
        int x = getX();
        int y = getY();
        int dir = getDirection();
        if(onEgg()){
                eggCount++;
        }
        while(! borderAhead()){
            move();
            if(onEgg()){
                eggCount++;
            }
        }
        goToLocation(x, y);
        setDirection(dir);
        return eggCount;
    }
    
    /**
     * walk around the fences it wil stop at an egg
     * 
     * <p> it will follow the left side if there are more boxes next to it
     */
    
    public void walkAroundFences(){
        int fenceSide = 0; // 0 is no fence 1 is left and 2 is richt
        
        // check if there is a fence and witch side
        turnLeft();
        if(fenceAhead()){
            fenceSide = 1;
            
        }else{
            turnRight();
            if(fenceAhead()){
                fenceSide = 2;
            }
            turnLeft();
        }
        
        // move
        if(fenceSide != 0){
            while(!onEgg()){
                if(fenceAhead()){
                    //if there is a fence forward try left
                    turnLeft();
                    if(fenceAhead()){
                        //if fence is also on the left try right 
                        turnAround();
                        if(fenceAhead()){
                            // if there is also a fence to the right turn back
                            turnRight();
                        }
                    }
                }
                move();
                if(fenceSide == 1){
                   turnLeft();
                   if(fenceAhead()){
                       turnRight();
                   }
                }else{
                   turnRight();
                   if(fenceAhead()){
                       turnLeft();
                   } 
                }
            }
        }
        
    }
    
    
      /**
     * solf any solid maze
     * 
     * <p> its the same a walkAroundFences() but it stop at een nest
     */
    public void solveSolidMaze(){
        int fenceSide = 0; // 0 is no fence 1 is left and 2 is richt
        
        // check if there is a fence and witch side
        turnLeft();
        if(fenceAhead()){
            fenceSide = 1;
        }
        turnAround();
        if(fenceAhead()){
            fenceSide = 2;
        }
        
        turnLeft();
        
        // move
        
        if(fenceSide != 0){
            while(!onNest()){
                if(fenceAhead()){
                    //if there is a fence forward try left
                    turnLeft();
                    if(fenceAhead()){
                        //if fence is also on the left try right 
                        turnAround();
                        if(fenceAhead()){
                            // if there is also a fence to the right turn back
                            turnRight();
                        }
                    }
                }
                move();
                if(fenceSide == 1){
                   turnLeft();
                   if(fenceAhead()){
                       turnRight();
                   }
                }else{
                   turnRight();
                   if(fenceAhead()){
                       turnLeft();
                   } 
                }
            }
        }
        
    }
    /**
     * it wil follow a trail of eggs and stop at a nest
     * 
     * <p> if there is a egg infornt of it wil move to it 
     * <p> if there is no egg infront of it the dodo wil check Right for an egg
     * <p> if there is no egg to the right it wil turn around and check the left side.
     */
    
    public void eggTrailToNest(){
        while(!onNest()){
            if(eggAhead() || nestAhead()){
                move();
            }else{
                turnRight();
                if(!eggAhead() && !nestAhead()){
                   turnAround(); 
                }
            }
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{
            return true;
        }
    }  
    
    /**
     * move to the first egg it can find.
     */
    public void goToEgg(){
        while(!onEgg()){
            if(borderAhead() || fenceAhead()){
                break; // stop when u can't go on
            }
            move();
        }
    }
    
     /**
     * turn 180 deg by turn right two times.
     */
    
    public void turnAround(){
        turnRight();
        turnRight();
    }
    
    
     /**
     * keeps turning right until facing east
     */
    public void faceEast(){
        while(getDirection() != 1){
            turnRight();
        }
    }
    
    /**
     * move to the set location
     * 
     * <p> it wil turn even if he is alread on the location but jumbing 0 is the same position
     */
    public void goToLocation(int x, int y){
        if(!validCoordinates(x, y)){
            return;
        }
        int changeX = getX() - x;
        int changeY = getY() - y;
        
        // ternary operator omdat ik geen zin heb om een voledige if else te maken.
        
        setDirection((changeX < 0) ? 1  : 3);
        
        jump((changeX > 0) ? changeX : changeX*-1);
        
        setDirection((changeY < 0) ? 2  : 0);
        
        jump((changeY > 0) ? changeY : changeY*-1);
    }
    
    /**
     * return true if the coordinates are in the world border
     */
    public boolean validCoordinates(int x, int y){
        if(x >= getWorld().getWidth() || x < 0){
            showError("Invalid coordinates");
            return false;
        }
        if(y >= getWorld().getHeight() || y < 0){
            showError("Invalid coordinates");
            return false;
        }
        return true;
    }
    
    public void makeMonument(){
        setDirection(1);
        
        int width = 1;
        boolean workSpace = true;
        while(workSpace){
            layEgg();
            layTrailOfEggs(width - 1);
            if(borderAhead()) workSpace = false;
            
            turnAround();
            jump(width - 1);
            turnLeft();
            if(borderAhead()) break; // yes no {} i? lazy
            move();
            turnLeft();
            width++;
        }
    }
}
