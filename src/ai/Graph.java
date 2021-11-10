package ai;

import java.io.*; 
import java.util.*; 
class Graph 
{ 
	
    static int V;   // No. of vertices (cities)
    static LinkedList<Edge> adjacent[]; //Adjacency Lists of vertices
    
    //cities are indexed according to its number
    static String[] cities= {"Arad","Timisioara","Sibiu","Zerind","Lugoj",
			"Rimnicu Vilcea","Fagarus","Oradea","Mehadia","Craiova"
			,"Pitesti","Bucharest","Drobeta","Giurgiu","Urziceni"
			,"Hirsova","Eforie","Vaslui","Iasi","Neamt"};
    private int result_cost=-1;		//final cost of goal
    private String result_path="";	//final path of goal
    private String result_traverse=""; //trace of fringe
    
    //every vertex has all previous vertices from the start state
    private List<Integer> each_vertic_path[];
    
    //Goal vertex has all previous vertices
    //It is the list of vertices that are lie between start to goal state
    private ArrayList<Integer> result_path_vertices=new ArrayList<>();
    
    Graph()
    {	
    	this(20, cities);//Total 20 cities in the Romania Map
    	this.makeGraph();//Add edges in the map graph
    }
  
    private Graph(int v,String[] cities) 
    { 
        this.V = v; 
        adjacent = new LinkedList[v];
        each_vertic_path=new ArrayList[V];
        for (int i=0; i<v; ++i) {
            adjacent[i] = new LinkedList();
            each_vertic_path[i]=new ArrayList<>();
        }
        this.cities=cities;
    }
    @Override
	public String toString(){
		String result="";
               
		for(int i=0;i<adjacent.length;i++)
            result+=cities[i]+"=>"+adjacent[i]+"\n";
		
		return result;
	}
    void BFS(int s,int goal) 
    { 
    	clear();	//remove vertices from the list of previous result
    	
        //list of visited vertices or cities
    	//the indexes are based on the value of the vertices
        boolean visited[] = new boolean[V];
        
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        
        visited[s]=true; //because it is at starting vertex
        queue.add(s);
        each_vertic_path[s].add(s);

        while (queue.size() != 0) 
        { 
        	result_traverse+=cities[s]+": "+getNameInQueue(queue)+"\n";
        	
            s = queue.poll();
            
            if(s==goal)break;
           
            Iterator<Edge> i = adjacent[s].listIterator(); 
            int n;
            
            while (i.hasNext()) 
            { 
                Edge e = i.next();
                n=e.vertex;
                
                if (!visited[n]) 
                {   
                	each_vertic_path[n].clear();//Other algo. add values from
                	each_vertic_path[n].addAll(each_vertic_path[s]);//add previous vertices
                	each_vertic_path[n].add(n);//add current vertex
                    visited[n] = true; 
                    queue.add(n);
                }//if
            }//inner-while 
        }//outer-while 
        result_traverse+=cities[s]+": "+getNameInQueue(queue)+"\n";
        result_path_vertices=(ArrayList<Integer>)each_vertic_path[s];
        result_cost=getCost(each_vertic_path[s]);
        result_path="Actual Path: "+getPath();
    }
   
    void DFS(int s,int goal) 
        { 
    		clear();
	    	Stack<Integer> stack = new Stack<>(); 
	    	/*we use vector because it appends the comming value 
	    	  at the end. it is same as stack. Vector use first in
	    	  last out mechanism (FILO)
	    	*/
            Vector<Boolean> visited = new Vector<Boolean>(V);
            for (int i = 0; i < V; i++)
                visited.add(false);
            
            stack.push(s);
            each_vertic_path[s].add(s);
            while(!stack.empty()) 
            { 
            	result_traverse+=cities[s]+": "+getNameInStack(stack)+"\n";
                s=stack.pop();
                
                if(!visited.get(s)) 
                    visited.set(s, true);
                
                if(s==goal)break; 
                
                Iterator<Edge> iterator = adjacent[s].iterator(); 
                  
                while (iterator.hasNext())  
                {   
                    Edge e = iterator.next();
                    int n=e.vertex;
                    if(!visited.get(n)){
                    	each_vertic_path[n].clear();
                    	each_vertic_path[n].addAll(each_vertic_path[s]);//add previous costs
                    	each_vertic_path[n].add(n);//add current cost
                    	
                        stack.push(n); 
                    } 
                }//inner-while    
            }//outer-while
            result_traverse+=cities[s]+": "+getNameInStack(stack)+"\n";
            result_path_vertices=(ArrayList<Integer>)each_vertic_path[s];
            result_cost=getCost(each_vertic_path[s]);
            result_path="Actual Path: "+getPath();
        } 
   
    void AST(int s,int goal) {
    	clear();
    	//index of hueristic is corresponding to name to the city and its index
    	//Cities are entered according to the index
    	//As 366 add at 0 index mean 0 is the number of Arad
    	//So the 366 is hueristic of Arad
    	int[] hueristic = {366,329,253,374,244,193,178,380,241,160
    						,98,0,242,77,80,151,161,199,226,234};
    	
    	//for comparator store every vertex hueristic+ActualCost
    	//The actual cost is from the start state cumulative cost
    	int[] hpc=new int[V]; 
    	Comparator<Integer> hueristicComparator= new Comparator<Integer>() {
			
			@Override
			public int compare(Integer o1, Integer o2) {
				
				return hpc[o1]-hpc[o2];
			}
		};
		
        //list of visited vertices or cities
    	//the indexes are based on the value of the vertices
        boolean visited[] = new boolean[V]; 
        PriorityQueue<Integer> queue = new PriorityQueue<>(hueristicComparator);
        
        hpc[s]=getCost(each_vertic_path[s])+hueristic[s];
        
        visited[s]=true; 
        queue.add(s);
        each_vertic_path[s].add(s);
        
        while (queue.size() != 0) 
        {
        	result_traverse+=cities[s]+": "+getNameInPriorityQueue(queue)+"\n";//for FX
            s = queue.poll();
            
            if(s==goal)break;
           
            Iterator<Edge> i = adjacent[s].listIterator(); 
            int n;
            
            while (i.hasNext()) 
            { 
                Edge e = i.next();
                n=e.vertex;
                
                if (!visited[n]) 
                {   
                	each_vertic_path[n].clear();
                	each_vertic_path[n].addAll(each_vertic_path[s]);//add previous costs
                	each_vertic_path[n].add(n);//add current cost
                	hpc[n]=getCost(each_vertic_path[n])+hueristic[n];
                	
                    visited[n] = true; 
                    queue.add(n);
                }//if
            }//inner-while 
        }//outer-while 
        result_traverse+=cities[s]+": "+getNameInPriorityQueue(queue)+"\n";
        result_path_vertices=(ArrayList<Integer>)each_vertic_path[s];
        result_cost=getCost(each_vertic_path[s]);
        result_path="Actual Path: "+getPath();
        
    }//AST
    
    public ArrayList<Integer> getPathVertices() 
    {
    	return result_path_vertices;
    }
    
    //return the traverse trace of the path
    public String getPathTraverse() 
    {
    	String s=result_traverse;
    	result_traverse="";		//clear previous path (avoid concatenation)
    	return s;
    }//getPathTriverse
    
    //return the cost of the goal path
    public int getCost() 
    {
    	return result_cost;
    }
    
    //return the array of cities in the graph
    public String[] getCities() 
    {
    	return cities;
    }//getCities
    
    //return the path in string format
    public String getPath() 
    {
    	String s="";
    	for (Integer vertex : result_path_vertices) {
			s+=cities[vertex]+"-->";
		}
    	return s;
    }//getPath
    
    //It is basically create he map
    //All edges are created in the map
    private void makeGraph() 
    {
    	/*
        0= Arad, 1=Timisioara, 2=Sibiu, 3=Zerind, 4=Lugoj
        5=Rimnicu Vilcea, 6=Fagarus, 7=Oradea, 8=Mehadia
        9=Craiova, 10=Pitesti, 11=Bucharest, 12=Drobeta
        13=Giurgiu, 14=Urziceni, 15=Hirsova, 16=Eforie
        17=Vaslui, 18=Iasi, 19=Neamt
         */
        //Arad-->Timisoara				Arad-->Sibiu
        this.addEdge(0, 1, 118); this.addEdge(0, 2, 140);
        //Arad-->Zerind
        this.addEdge(0, 3, 75); 
        
        //Timisoara-->Arad				Timisoara-->Lugoj
        this.addEdge(1, 0, 118); this.addEdge(1, 4, 111);
        
        //Sibiu-->Arad					Sibiu-->Rimnicu Vilcea
        this.addEdge(2, 0, 140); this.addEdge(2, 5, 80);
        //Sibiu-->Fagaras				Sibiu-->Oradea
        this.addEdge(2, 6, 99);  this.addEdge(2, 7, 151);
        
        //Zerind-->Arrad				Zerind-->Oradea
        this.addEdge(3, 0, 75); this.addEdge(3, 7, 71);
        
        //Lugoj-->Timisoara				Lugoj-->Mehadia
        this.addEdge(4, 1, 111); this.addEdge(4, 8, 70);
        
        //Rimnicu Vilcea-->Sibiu		Rimnicu Vilcea-->Craiova
        this.addEdge(5, 2, 80); this.addEdge(5, 9, 146);
        //Rimnicu Vilcea-->Pitesti
        this.addEdge(5, 10, 97);
        
        //Fagaras-->Sibiu				Fagaras-->Bucharest
        this.addEdge(6, 2, 99); this.addEdge(6, 11, 211);
        
        //Oradea-->Zerind				Oradea-->Sibiu
        this.addEdge(7, 3, 71); this.addEdge(7, 2, 151);
        
        //Mehadia-->Lugoj				Mehadia-->Drobeta
        this.addEdge(8, 4, 70); this.addEdge(8, 12, 75);
        
        //Craiova-->Drobeta				Craiova-->Pitesti
        this.addEdge(9, 12, 120); this.addEdge(9, 10, 138);
        //Craiova-->Rimnicu Vilcea
        this.addEdge(9, 5, 146);
        
        //Pitesti-->Craiova				Pitesti-->Bucharest
        this.addEdge(10, 9, 138); this.addEdge(10, 11, 101);
        //Pitesti-->Rimnicu Vilcea
        this.addEdge(10, 5, 97);
        
        //Bucharest-->Pitesti			Bucharest-->Fagaras
        this.addEdge(11, 10, 101); this.addEdge(11, 6, 211);
        //Bucharest-->Giurgiu			Bucharest-->Urzeceni
        this.addEdge(11, 13, 90); this.addEdge(11, 14, 85);
     
        //Drobeta-->Mehadia				Drobeta-->Craiova
        this.addEdge(12, 8, 75); this.addEdge(12, 9, 120);
        
        //Giurgiu-->Bucharest
        this.addEdge(13, 11, 90);
        
        //Urzeceni-->Bucharest			Urzeceni-->Hirsova
        this.addEdge(14, 11, 85); this.addEdge(14, 15, 98);
        //Urzeceni-->Vaslui
        this.addEdge(14, 17, 142);
        
        //Hirsova-->Urzeceni			Hirsova-->Eforie
        this.addEdge(15, 14, 98); this.addEdge(15, 16, 86);
        
        //Eforie-->Hirsova
        this.addEdge(16, 15, 86);
        
        //Vaslui-->Urzeceni				Vaslui-->Iasi
        this.addEdge(17, 14, 142); this.addEdge(17, 18, 92);
        
        //Iasi-->Vaslui					Iasi-->Neamt
        this.addEdge(18, 17, 92); this.addEdge(18, 19, 87);
        
        //Neamt-->Iasi
        this.addEdge(19, 18, 87);
    }//makeGraph
    
    //add edges in the graph between two vertices and the weight between them
    private void addEdge(int fromVertex,int toVertex,int weight) 
    { 
        Edge e=new Edge(toVertex,weight);
        adjacent[fromVertex].add(e);
    }//addedge
    
    //clear all list in the array of each_vertice_path
    private void clear() 
    {
        for (int i = 0; i <each_vertic_path.length ; i++)
        		each_vertic_path[i].clear();
    } //clear
    
    //return a string of cities name of all vertices in the PriorityQueue
    private String getNameInPriorityQueue(PriorityQueue<Integer> queue) 
    {
    	String s="";
    	for (Integer integer : queue)
			s+=cities[integer]+"-->";
    	return s;
    }//getNameInPriorityQueue
    
    //return a string of cities name of all vertices in the stack
    private String getNameInStack(Stack<Integer> stack) {
    	String s="";
    	for (Integer vertex : stack)
    		s+=cities[vertex]+"-->";
    	return s;
    }//getNameInStack
    
    //return the cost of edges between the vertices in the list
    private int getCost(List<Integer> l) 
    {
    	int cost=0;
    	int from=0,to=0,size=l.size();
    	for (int i = 0; i < size-1; i++) {
    			from=l.get(i);
    			to=l.get(i+1);
    			
    			LinkedList<Edge> list=adjacent[from];
    			for (int j = 0; j < list.size(); j++) {
					if (list.get(j).vertex==to) {
						to=j;
						break;
					}
				}//inner-for
    			cost+=adjacent[from].get(to).weight;
		}//for
    	return cost;
    }//getCost
    
    //return a string of cities name of all vertices in the queue 
    private String getNameInQueue(LinkedList<Integer> queue) {
    	String s="";
    	for (Integer value : queue) {
			s+=cities[value]+"--> ";	
		}
    	return s;
    }


    
}//class

