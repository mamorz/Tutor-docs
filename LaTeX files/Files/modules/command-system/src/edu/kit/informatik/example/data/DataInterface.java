package edu.kit.informatik.example.data;

/**
 * The {@code DataInterface} defines a interface on how to interact with the
 * data, i.e. the game or storage.
 *
 * @author Aurelia Huell
 * @version 1.0
 */
public interface DataInterface {

    /*
     * Nearly empty for this example.
     *
     * This could be methods like storeSomething(), removeSometing(),
     * placeToken(), getResult(), etc. depending on your data.
     */

    /**
     * Reset the data.
     *
     * @return a string for a successful reset
     */
    String reset();

    /**
     * Add an entity.
     *
     * @param entity random entity
     * @return a string for a successful addition
     */
    String addEntity(String entity);

}
