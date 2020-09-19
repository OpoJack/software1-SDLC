package module2;

public class Test { 
    public static void main(String[] args) 
    {        
        String text = "the quick brown fox jumps fox fox over the lazy dog brown"; 
        String[] poemLIST = text.split(" "); 
        String[] uniqueKeys; 
        int count = 0; 
        System.out.println(text); 
        uniqueKeys = getUniqueKeys(poemLIST); 
        for(String key: uniqueKeys) 
        { 
            if(null == key) 
            { 
                break; 
            }            
            for(String s : poemLIST) 
            { 
                if(key.equals(s)) 
                { 
                    count++; 
                }                
            } 
            System.out.println("Count of ["+key+"] is : "+count); 
            count=0; 
        } 
    } 
    private static String[] getUniqueKeys(String[] keys) 
    { 
        String[] uniqueKeys = new String[keys.length]; 
        uniqueKeys[0] = keys[0]; 
        int uniqueKeyIndex = 1; 
        boolean keyAlreadyExists = false; 
        for(int i=1; i<keys.length ; i++) 
        { 
            for(int j=0; j<=uniqueKeyIndex; j++) 
            { 
                if(keys[i].equals(uniqueKeys[j])) 
                { 
                    keyAlreadyExists = true; 
                } 
            }            
            if(!keyAlreadyExists) 
            { 
                uniqueKeys[uniqueKeyIndex] = keys[i]; 
                uniqueKeyIndex++;                
            } 
            keyAlreadyExists = false; 
        }        
        return uniqueKeys; 
    } 
} 