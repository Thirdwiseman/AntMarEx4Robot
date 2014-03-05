package util;


import java.util.ArrayList;
import java.util.Iterator;


public class QueueAgenda<E extends Comparable<E>> implements interfaces.Agenda<E> {
	
	private ArrayList<E> queue;
	private boolean aStar;
	
	
	public QueueAgenda(boolean aStar){
		queue = new ArrayList<E>();
		this.aStar = aStar;
	}
	
	@Override
	public E pop() {
		E pop = queue.get(0);
		queue.remove(0);
		return pop;
	}

	@Override
	public boolean isEmpty() {
		
		return queue.size() == 0;
	}



	@Override
	public void push(E _item) {
		
		if(aStar && !queue.isEmpty()){
			int count = -1;
			do{
				count ++;
			} while (count < queue.size() && _item.compareTo(queue.get(count)) > 0);
			queue.add(count, _item);	
			
		} else{
			queue.add(_item);
		}
		
	}

	@Override
	public boolean contains(E _item) {

		return queue.contains(_item);
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getSize(){
		return queue.size();
	}


}