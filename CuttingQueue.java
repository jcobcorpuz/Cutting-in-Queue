import java.util.ArrayList;
import java.util.Comparator;

public class CuttingQueue {
    public static void main(String[] args){
        JobQueue jobQueue = new JobQueue();

        Job jobA = new Job("This is job a", 5);
        Job jobB = new Job("This is job b", 2);
        Job jobC = new Job("This is job c", 9);
        Job jobD = new Job("This is job d", 8);
        Job jobE = new Job("This is job e", 1);
        jobQueue.insert(jobA);
        jobQueue.insert(jobB);
        jobQueue.insert(jobC);
        jobQueue.insert(jobD);
        jobQueue.insert(jobE);

        jobQueue.runHighestPriority();
        jobQueue.runHighestPriority();
        jobQueue.runHighestPriority();
        jobQueue.runHighestPriority();
        jobQueue.runHighestPriority();
    }
}

class MaxHeap<T extends Comparable<T>>{
    private ArrayList<T> heap;

    public MaxHeap(){
        heap = new ArrayList<>();
    }

    private void swap(int i, int j){
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insert(T value){
        heap.add(value);
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if(heap.get(index).compareTo(heap.get(parentIndex)) > 0){
                swap(index, parentIndex);
                index = parentIndex;
            }
            else{
                break;
            }
        }
    }
    private void fixOrder(int index){
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0){
            largest = left;

        }
        if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0){
            largest = right;
        }
        if (largest != index) {
            swap(index, largest);
            fixOrder(largest);
        }
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public T extractMax(){
        if(heap.isEmpty()){
            return null;
        }
        if (heap.size() == 1) {
            return heap.remove(0);
        }
        T max = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        fixOrder(0);
        return max;

    }
}

class PriorityQueue<T extends Comparable<T>>{
    private MaxHeap<T> maxHeap;

    public PriorityQueue(){
        maxHeap = new MaxHeap<>();
    }

    public void add(T item){
        maxHeap.insert(item);
    }

    public T poll(){
        return maxHeap.extractMax();
    }
    public boolean isEmpty(){
        return maxHeap.isEmpty();
    }
}

class Job implements Comparable<Job>{
    private String name;
    private int priority;

    Job(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    void execute() {
        System.out.println("Running the job with name " + this.name + " and priority " + this.priority);
    }

    String getName() {
        return this.name;
    }

    int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(Job other) {
        return Integer.compare(this.priority, other.priority);
    }
}

class JobQueue{
    private PriorityQueue<Job> priorityQueue;

    public JobQueue(){
        this.priorityQueue = new PriorityQueue<>();
    }

    public void insert(Job job){
        priorityQueue.add(job);
    }

    public void runHighestPriority(){
        if(!priorityQueue.isEmpty()){
            Job highestPriorityJob = priorityQueue.poll();
            highestPriorityJob.execute();
        }
        else{
            System.out.println("No jobs in queue");
        }
    }
}
