import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class Graph {
    
    private ArrayList<ArrayList<String>> edges;
    private ArrayList<String> vertices;
    
    
    public Graph(String[] v, String[][] e){
        edges = new ArrayList<>();
        vertices = new ArrayList<>(Arrays.asList(v));
        
        for(String [] edge : e){
            if(edge.length == 2){
                edges.add(new ArrayList<>(Arrays.asList(edge)));
            }     
        }
        notBeCaseSensitive();
        notHaveDuplicateVerticesEdges();    
    }
    
    public boolean contains(String vertex){
        for(String v : vertices){
            if (v.equals(vertex)){
                return true;
            }
        }
        return false;
    }
    
    public Graph path(String start, String end){
        ArrayList<String> arr = new ArrayList<>(); 
        
        boolean[] visited = new boolean[vertices.size()];
        Queue<Integer> queue = new LinkedList<>();
        
        int cont_start = 0;
        int cont_end = 0;
        
        for(int i = 0; i < vertices.size(); i++){
            if(vertices.get(i).equals(start)){
                cont_start = i;
            }
            if(vertices.get(i).equals(end)){
                cont_end = i;
            }
        }
        
        visited[cont_start] = true;
        queue.add(cont_start);   
        
        while (!queue.isEmpty()){
            int current = queue.poll(); 
            arr.add(vertices.get(current));
            for(int neighbor = 0; neighbor < edges.get(current).size(); neighbor++){
                if(edges.get(current).get(neighbor) != null && !visited[neighbor]){
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        for(String elemento : arr){
            System.out.println(elemento);
        }
        
        return null;
    }

    public Graph union (Graph g){
        
        return null;
    }

    
    public int vertices(){
        return vertices.size();
    }
    
    public int edges(){
        return edges.size();
    }    
    
    
    public boolean equals(Graph g){
        for (int i = 0; i < vertices.size(); i++){
            if(!vertices.get(i).equals(g.vertices.get(i))){
                return false;
            }
        }
        
        for(int j = 0; j < edges.size(); j++){
            for(int k = 0; k < edges.get(j).size(); k++){
                if (!edges.get(j).get(k).equals(g.edges.get(j).get(k))){
                    return false;
                }
                
            }
        }
        return true;
    }
    
    public boolean equals(Object g){
        return equals((Graph)g);
    }
    
    //Only arcs in space-separated tuples. The vertices are capitalized. The edges must always be in alphabetical order.
    //For example, (A, B) (C, D) 
    @Override
    public String toString() {
        StringBuilder parejas = new StringBuilder();
        
        // Funcion para organizar en orden alfabetico
        edges.sort((edge1, edge2) -> {
            String edge1Str = edge1.get(0) + edge1.get(1);
            String edge2Str = edge2.get(0) + edge2.get(1);
            return edge1Str.compareTo(edge2Str);
            
        });
        
        //Analiza arista por arista y concatena los elementos como se pide en el test
        for(ArrayList<String> edge : edges){
            
            parejas.append("(").append(edge.get(0)).append(", ").append(edge.get(1)).append(") ");
        }
        
        return parejas.toString().trim(); // trim elimina los espacios y retorna el string
    }
    //---------------------------Metodos Creados--------------------------------------------------//

    public void notBeCaseSensitive(){
        for(int i = 0; i < vertices.size(); i++){
            vertices.set(i, vertices.get(i).toUpperCase());
        }
        for(int j = 0; j< edges.size(); j++){
                for(int k = 0; k < edges.get(j).size(); k++){
                    edges.get(j).set(k, edges.get(j).get(k).toUpperCase());
            }
        }
    }
    
    public void notHaveDuplicateVerticesEdges(){
        ArrayList<String> updatedVList = new ArrayList<>();
        ArrayList<ArrayList<String>> updatedEList = new ArrayList<ArrayList<String>>();
        for (String vertex: vertices){
            if(!updatedVList.contains(vertex)){
                updatedVList.add(vertex);
            }
    
        }
        for (ArrayList<String> edge: edges){
            if(!updatedEList.contains(edge)){
                updatedEList.add(edge);
            }
        }
        vertices = updatedVList;
        edges = updatedEList;
    }

}
