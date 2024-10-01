/**
 * NoSuchException is an abstract class representing a custom exception for
 * cases where a requested entity (such as an account or bank) cannot be found.
 * It requires subclasses to implement methods for retrieving the entity's name
 * and its unique identifier (ID).
 *
 * Subclasses should represent specific exceptions such as NoSuchAccount or NoSuchBank,
 * providing meaningful messages when the requested entity does not exist.
 */
public abstract class NoSuchException extends Exception {

    /**
     * Returns the name of the entity that could not be found.
     * Subclasses must override this method to return the appropriate entity name.
     *
     * @return the name of the missing entity.
     */
    public abstract String getName();

    /**
     * Returns the unique ID of the entity that could not be found.
     * Subclasses must override this method to return the relevant ID.
     *
     * @return the unique ID of the missing entity.
     */
    public abstract int getId();

    /**
     * Overrides the default toString method to provide a detailed error message
     * indicating that the entity with the specified ID could not be found.
     *
     * @return a string message indicating the absence of the entity.
     */
    @Override
    public String toString() {
        return "No Such " + getName() + " With Id: " + getId();
    }
}
