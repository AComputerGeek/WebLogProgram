import java.util.*;

/**
* 
* @author: Amir Armion 
* 
* @version: V.01
* 
*/
public class Tester
{
    public void testLogEntry() 
    {
        LogEntry le1 = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le1);

        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    public void testLogAnalyzer() 
    {
        LogAnalyzer le1 = new LogAnalyzer();

        le1.readFile("weblog2_log");
        le1.printAll();

        System.out.println("\n- Number of unique IPs is: " + le1.countUniqueIPs() + " IPs");

        System.out.println("\n- These logs have status more than 400: ");
        le1.printAllHigherThanNum(400);

        System.out.println("\n- These unique IP addresses that had access on Sep 27: ");
        for(int i = 0; i < le1.uniqueIPVisitsOnDay("Sep 27").size(); i++)
        {
            System.out.println(le1.uniqueIPVisitsOnDay("Sep 27").get(i));
        }
        System.out.println("\n- Number of unique IP addresses that had access on Sep 27 is: " + le1.uniqueIPVisitsOnDay("Sep 27").size());

        System.out.println("\n- Number of unique IPs that have a status code in the range 200 to 299 is:");
        System.out.println(le1.countUniqueIPsInRange(200, 299));

        System.out.println("\n- All visits unique IP and how many time visits is: : ");
        HashMap<String, Integer> visits = le1.countVisitsPerIP();
        for(String key: visits.keySet())
        {
            System.out.println("IP: \"" + key + "\", visits: " + visits.get(key) + " times");
        }
        System.out.println("\n- All visitors per IP is: " + visits);

        System.out.println("\n- The maximum number(most number visit per ip) of visit is: " + le1.mostNumberVisitsByIP(visits));

        System.out.println("\n- All the most visited IPs is: ");
        ArrayList<String> mostVisitIps = le1.iPsMostVisits(visits);
        for(int i = 0; i < mostVisitIps.size(); i++)
        {
            System.out.println(mostVisitIps.get(i));
        }

        System.out.println("\n- All IPs for log file weblog3-short_log is: ");
        HashMap<String, ArrayList<String>> allIpsOnDay = le1.iPsForDays();
        System.out.println(allIpsOnDay);
        for(String key: allIpsOnDay.keySet())
        {
            System.out.println("\n- Day: " + key);

            for(int i = 0; i < allIpsOnDay.get(key).size(); i++)
            {
                System.out.println("IP: " + allIpsOnDay.get(key).get(i));
            }
        }
        
        System.out.println("\n- The day that has the most IP address visits is: " + le1.dayWithMostIPVisits(allIpsOnDay));
        
        System.out.println("\n - All IPs addresses that had the most accesses on Sep 30 is: ");
        ArrayList mostVisited = le1.iPsWithMostVisitsOnDay(allIpsOnDay, "Sep 30");
        for(int i = 0; i < mostVisited.size(); i++)
        {
            System.out.println(mostVisited.get(i));
        }
    }
}

