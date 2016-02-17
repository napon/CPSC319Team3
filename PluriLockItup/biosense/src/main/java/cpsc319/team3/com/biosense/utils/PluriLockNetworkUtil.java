package cpsc319.team3.com.biosense.utils;

import cpsc319.team3.com.biosense.models.PluriLockPackage;

/**
 * This class is responsible for:
 * - Establishing connection with the PluriLock Server
 * - Sending PluriLockEvent information using the protocol specified by PluriLock
 * - Establishing connection with the OfflineDatabaseUtil
 * - Storing PluriLockEvent information in the local database when there is no network connection
 * - Fetching data from the OfflineDatabaseUtil
 * - Reacting to changes in the network connectivity of the client's device
 * - Anonymizing client's data before sending it to the server
 * - Parsing the response from the server and passing it to the corresponding listener
 *
 * See the UML Diagram for more implementation details.
 */
public class PluriLockNetworkUtil {

    /**
     * Sends a PluriLockPackage to the server when there is internet connectivity
     * or stores it in the local database when there is no network connection.
     * @param pluriLockPackage
     */
    public void sendEvent(PluriLockPackage pluriLockPackage) {
        // TODO
    }
}
