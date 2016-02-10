package cpsc319.team3.com.biosense.models;

/**
 * PluriLockEvent is an abstract model class that represents any kind of event tracked by our API.
 * The reason for this class is so that every event recorded by our API can be put into a list and
 * send to the PluriLock Server.
 *
 * A PluriLockEvent is one of:
 * - PElementTouchEvent
 * - PKeyboardTouchEvent
 * - PScrollEvent
 *
 * (There might be more in the future.. They are listed in the models/ package.)
 *
 * This class contains a generic set of properties that are common to all touch events.
 * These include:
 * - eventID
 * - screenOrientation
 * - timestamp
 * - gpsLocation
 *
 * See UML Diagram for more implementation details.
 */
public abstract class PluriLockEvent {

}
