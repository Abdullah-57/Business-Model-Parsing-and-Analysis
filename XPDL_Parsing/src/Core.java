import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class Core
{
    public static void main(String[] args)
    {
        try
        {
            // Loading the XML file
            File xmlFile = new File("C:/Users/Admin/OneDrive/Desktop/Portfolio Projects/5/Process Mining/Group D_Section E_Project/Group D_Section E_Project/Core.xpdl");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            // Assigning random times to all Tasks
            addActivityTime(document);

            // Calculating total Cycle Time
            int cycleTime = calculateCT(document);
            System.out.println("Total Cycle Time: " + cycleTime + " minutes");

            // Saving the updated XML document
            saveDocument(document, "C:/Users/Admin/OneDrive/Desktop/Portfolio Projects/5/Process Mining/Group D_Section E_Project/Group D_Section E_Project/Updated_Core.xpdl");


            // Counting all BPMN elements used in the model
            int totalEventCount = getElementCount(document, "Event");
            int startEventCount = getElementCountWithAttribute(document, "StartEvent", "Trigger", "None");
            int intermediateEventCount = getElementCountWithAttribute(document, "IntermediateEvent", "Trigger", "Message");
            int endEventCount = getElementCount(document, "EndEvent");

            int totalActivityCount = getElementCount(document, "Activity");
            int taskCount = getElementCount(document, "Implementation");
            int userTaskCount = getElementCountWithAttribute(document, "TaskUser", "Implementation", "Unspecified");
            int serviceTaskCount = getElementCountWithAttribute(document, "TaskService", "", "");
            int scriptTaskCount = getElementCountWithAttribute(document, "Task", "Type", "Script");
            int manualTaskCount = getElementCountWithAttribute(document, "Task", "Type", "Manual");
            int receiveTaskCount = getElementCount(document, "TaskReceive"); // New count for receive tasks
            int subProcessCount = getElementCount(document, "SubProcess");

            int totalGatewayCount = getElementCount(document, "Gateway") + getElementCount(document, "Route"); // Count both Gateways and Routes
            int exclusiveGatewayCount = getElementCountWithAttribute(document, "Route", "GatewayType", "Exclusive");
            int parallelGatewayCount = getElementCountWithAttribute(document, "Route", "GatewayType", "Parallel");
            int inclusiveGatewayCount = getElementCountWithAttribute(document, "Route", "GatewayType", "Inclusive");

            int totalArtifactCount = getElementCount(document, "Artifact");
            int dataObjectCount = getElementCountWithAttribute(document, "Artifact", "Type", "DataObject");
            int groupCount = getElementCountWithAttribute(document, "Artifact", "Type", "Group");
            int annotationCount = getElementCountWithAttribute(document, "Artifact", "Type", "Annotation");

            int totalConnectingObjectCount = getElementCount(document, "Transition") + getElementCount(document, "MessageFlow") + getElementCount(document, "Association");

            int sequenceFlowCount = getElementCount(document, "Transition");
            int messageFlowCount = getElementCount(document, "MessageFlow");
            int associationCount = getElementCount(document, "Association");

            int totalSwimlaneCount = getElementCount(document, "Lane") + getElementCount(document, "Pool");
            int poolCount = getElementCount(document, "Pool");
            int laneCount = getElementCount(document, "Lane");

            // Generating Report
            System.out.println("BPMN Model Elements:");
            System.out.println("Total Events: " + totalEventCount);
            System.out.println("Start Events: " + startEventCount);
            System.out.println("Intermediate Events: " + intermediateEventCount);
            System.out.println("End Events: " + endEventCount);
            System.out.println("Total Activities: " + totalActivityCount);
            System.out.println("Tasks: " + taskCount);
            System.out.println("User Tasks: " + userTaskCount);
            System.out.println("Service Tasks: " + serviceTaskCount);
            System.out.println("Script Tasks: " + scriptTaskCount);
            System.out.println("Manual Tasks: " + manualTaskCount);
            System.out.println("Receive Tasks: " + receiveTaskCount);
            System.out.println("Sub Processes: " + subProcessCount);
            System.out.println("Total Gateways: " + totalGatewayCount);
            System.out.println("Exclusive Gateways (XOR): " + exclusiveGatewayCount);
            System.out.println("Parallel Gateways (AND): " + parallelGatewayCount);
            System.out.println("Inclusive Gateways (OR): " + inclusiveGatewayCount);
            System.out.println("Total Artifacts: " + totalArtifactCount);
            System.out.println("Data Objects: " + dataObjectCount);
            System.out.println("Groups: " + groupCount);
            System.out.println("Annotations: " + annotationCount);
            System.out.println("Total Connecting Objects: " + totalConnectingObjectCount);
            System.out.println("Sequence Flows: " + sequenceFlowCount);
            System.out.println("Message Flows: " + messageFlowCount);
            System.out.println("Associations: " + associationCount);
            System.out.println("Total Swimlanes: " + (totalSwimlaneCount-1));
            System.out.println("Pools: " + (poolCount-1));
            System.out.println("Lanes: " + laneCount);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // addActivityTime function assigns random times between 5 to 15 minutes to all activity tags containing both "Implementation" and "Task" Tags
    private static void addActivityTime(Document document)
    {
        NodeList activities = document.getElementsByTagName("Activity");
        Random random = new Random();

        for (int i = 0; i < activities.getLength(); i++)
        {
            Node node = activities.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element activity = (Element) node;

                // Boolean variable to check for "Implementation" and "Task" tags inside "Activity" tags
                boolean hasTaskInImplementation = false;

                // Looping through children of "Activity" tags to find "Implementation" and "Task" tags
                NodeList children = activity.getChildNodes();

                for (int j = 0; j < children.getLength(); j++)
                {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("Implementation"))
                    {
                        NodeList implementationChildren = child.getChildNodes();
                        for (int k = 0; k < implementationChildren.getLength(); k++)
                        {
                            Node implementationChild = implementationChildren.item(k);
                            if (implementationChild.getNodeType() == Node.ELEMENT_NODE && implementationChild.getNodeName().equals("Task"))
                            {
                                hasTaskInImplementation = true;
                                break;
                            }
                        }
                    }
                }

                // If both "Implementation" and "Task" are found, assign a random time between 5 to 15 minutes
                if (hasTaskInImplementation)
                {
                    int randomTime = 5 + random.nextInt(11); // 11 for inclusive range [5, 15]
                    activity.setAttribute("Duration", randomTime + " minutes");
                }
            }
        }
    }

    // Helper function to save the updated XML document
    private static void saveDocument(Document document, String outputFilePath) throws Exception
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(outputFilePath));
        transformer.transform(source, result);
    }

    // Helper function to count elements by tag name
    private static int getElementCount(Document document, String tagName)
    {
        NodeList nodeList = document.getElementsByTagName(tagName);
        return nodeList.getLength();
    }

    // Helper function to count elements with a specific attribute value
    private static int getElementCountWithAttribute(Document document, String tagName, String attributeName, String attributeValue)
    {
        NodeList nodeList = document.getElementsByTagName(tagName);
        int count = 0;

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                String value = element.getAttribute(attributeName);

                if (value.equals(attributeValue))
                {
                    count++;
                }
            }
        }
        return count;
    }

    // Function to calculate Total Cycle Time (CT) of the process
    private static int calculateCT(Document document)
    {
        Map<String, Integer> activityDurations = new HashMap<>();
        Map<String, List<String>> predecessors = new HashMap<>();

        // Populating activity durations and predecessors
        NodeList activities = document.getElementsByTagName("Activity");

        for (int i = 0; i < activities.getLength(); i++)
        {
            Element activity = (Element) activities.item(i);
            String activityId = activity.getAttribute("Id");
            String durationStr = activity.getAttribute("Duration");

            if (!durationStr.isEmpty())
            {
                int duration = Integer.parseInt(durationStr.split("\\s+")[0]);
                activityDurations.put(activityId, duration);
            }

            NodeList incomingFlows = activity.getElementsByTagName("Incoming");
            List<String> predList = new ArrayList<>();

            for (int j = 0; j < incomingFlows.getLength(); j++)
            {
                Element incomingFlow = (Element) incomingFlows.item(j);
                String sourceId = incomingFlow.getAttribute("Source");
                predList.add(sourceId);
            }

            predecessors.put(activityId, predList);
        }

        // Calculating total duration considering parallel paths
        int totalDuration = 0;

        for (String activityId : activityDurations.keySet())
        {
            int maxPathDuration = getMaxPathDuration(activityId, activityDurations, predecessors);
            totalDuration = Math.max(totalDuration, maxPathDuration);
        }

        return totalDuration;
    }

    // Helper function to calculate maximum path duration
    private static int getMaxPathDuration(String activityId, Map<String, Integer> activityDurations, Map<String, List<String>> predecessors)
    {
        if (!predecessors.containsKey(activityId) || predecessors.get(activityId).isEmpty())
        {
            return activityDurations.get(activityId);
        }

        int maxPredecessorDuration = 0;

        for (String predId : predecessors.get(activityId))
        {
            int predDuration = getMaxPathDuration(predId, activityDurations, predecessors);
            maxPredecessorDuration = Math.max(maxPredecessorDuration, predDuration);
        }

        return maxPredecessorDuration + activityDurations.get(activityId);
    }
}