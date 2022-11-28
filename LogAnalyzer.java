import java.util.*;
import edu.duke.*;

/**
* 
* @author: Amir Armion 
* 
* @version: V.01
* 
*/
public class LogAnalyzer
{
    private ArrayList<LogEntry> records;

    public LogAnalyzer() 
    {
        records = new ArrayList<>();
    }

    public void readFile(String filename)
    {
        FileResource fr = new FileResource(filename);

        for(String line: fr.lines())
        {
            records.add(WebLogParser.parseEntry(line));   
        }
    }

    public void printAll() 
    {
        System.out.println("\n- All records: ");

        for(LogEntry le: records) 
        {
            System.out.println(le);
        }
    }
    
    public int countUniqueIPs()
    {
        ArrayList<String> uniqueIPs = new ArrayList<>();

        for(LogEntry record: records)
        {
            String ip = record.getIpAddress();

            if(!uniqueIPs.contains(ip))
            {
                uniqueIPs.add(ip);
            }
        }

        return uniqueIPs.size();
    }

    public void printAllHigherThanNum(int num)
    {
        for(LogEntry record: records)
        {
            int status = record.getStatusCode();

            if(status > num)
            {
                System.out.println(record);
            }
        }
    }

    // MMM is the first three characters of the month name with the first letter capitalized and the others in lowercase, 
    // and DD is the day in two digits 
    public ArrayList<String> uniqueIPVisitsOnDay(String someday)
    {
        ArrayList<String> uniqueIPs = new ArrayList<>();

        for(LogEntry record: records)
        {
            if(!uniqueIPs.contains(record.getIpAddress()) && record.getAccessTime().toString().contains(someday))
            {
                uniqueIPs.add(record.getIpAddress());
            }
        }

        return uniqueIPs;
    }

    public int countUniqueIPsInRange(int low, int high)
    {
        ArrayList<String> uniqueIPs = new ArrayList<>();

        for(LogEntry record: records)
        {
            int status = record.getStatusCode();

            if(status >= low && status <= high)
            {
                if(!uniqueIPs.contains(record.getIpAddress()))
                {
                    uniqueIPs.add(record.getIpAddress());
                }
            }
        }

        return uniqueIPs.size();
    }

    public HashMap<String, Integer> countVisitsPerIP()
    {
        HashMap<String, Integer> visits = new HashMap<>();

        for(LogEntry record: records)
        {
            String ip = record.getIpAddress();

            if(!visits.containsKey(ip))
            {
                visits.put(ip, 1);
            }
            else
            {
                visits.put(ip, visits.get(ip) + 1);
            }
        }

        return visits;
    }

    public int mostNumberVisitsByIP(HashMap<String, Integer> visits)
    {
        int maxVisit = 0;

        for(String key: visits.keySet())
        {
            if(visits.get(key) > maxVisit)
            {
                maxVisit = visits.get(key);
            }
        }

        return maxVisit;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> visits)
    {
        ArrayList<String> mostVisitIps = new ArrayList<>();

        int maxVisit = 0;

        for(String key: visits.keySet())
        {
            if(visits.get(key) > maxVisit)
            {
                maxVisit = visits.get(key);
            }
        }

        for(String key: visits.keySet())
        {
            if(visits.get(key) == maxVisit)
            {
                mostVisitIps.add(key);
            }
        }

        return mostVisitIps;
    }

    public HashMap<String, ArrayList<String>> iPsForDays()
    {
        HashMap<String, ArrayList<String>> allIpsOnDay = new HashMap<>();

        for(LogEntry record: records)
        {
            ArrayList<String> ips = new ArrayList<>();

            String date           = record.getAccessTime().toString();
            String trimedDate     = date.substring(4, 10);
            String ip             = record.getIpAddress();

            if(!allIpsOnDay.containsKey(trimedDate))
            {
                ips.add(ip);
                allIpsOnDay.put(trimedDate, ips);
            }
            else
            {
                allIpsOnDay.get(trimedDate).add(ip);
            }            
        }

        return allIpsOnDay;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> allIpsOnDay)
    {
        int    maxVal = 0;
        String maxKey = null;

        for(String key: allIpsOnDay.keySet())
        {
            if(allIpsOnDay.get(key).size() > maxVal)
            {
                maxVal = allIpsOnDay.get(key).size();
                maxKey = key;
            }
        }

        return maxKey;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> allIpsOnDay, String someday)
    {
        ArrayList<String>        ipsMostVisitOnDay = new ArrayList<>();
        ArrayList<String>        mostVisited       = new ArrayList<>();
        HashMap<String, Integer> visits            = new HashMap<>();

        for(String key: allIpsOnDay.keySet())
        {
            if(key.equals(someday))
            {
                for(int i = 0; i < allIpsOnDay.get(key).size(); i++)
                {
                    String ip = allIpsOnDay.get(key).get(i);

                    if(!ipsMostVisitOnDay.contains(ip))
                    {
                        ipsMostVisitOnDay.add(ip);
                        visits.put(ip, 1);
                    }
                    else
                    {
                        visits.put(ip, visits.get(ip) + 1);
                    }
                }
            }
        }

        mostVisited = iPsMostVisits(visits);

        return mostVisited;
    }
}
