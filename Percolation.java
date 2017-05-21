import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation
{
   private int rowL;
   private int topIndex;
   private int bottomIndex;
   private int gridSize;
   private boolean[] grid;
   private boolean percolates;
   private WeightedQuickUnionUF uf;
   public Percolation(int N)
   {
       if(N<=0)
       {
           throw new java.lang.IllegalArgumentException
               (
                "N has to be larger than than 0");
       }
       rowL=N;
       gridSize=N*N;
       uf= new WeightedQuickUnionUF(gridSize+2);
       topIndex=gridSize;
       bottomIndex=gridSize+1;
   }
   private void checkInput(int i,int j)
   {
       if(i<1||i>rowL)
       {
           throw new java.lang.IndexOutOfBoundsException
               (
                "i should be between 1 and "+rowL
                    );
       }
        if(j<1||j>rowL)
       {
           throw new java.lang.IndexOutOfBoundsException
               (
                "j should be between 1 and "+rowL
                    );   
        }
   }                                               
    public void open(int x,int y)
    {
        checkInput(x,y);
        int i=x-1;
        int j=y-1;
        int index=getIndex(i,j);
        if(!grid[index])
        {
            grid[index]=true;
            int n;
            boolean hasN=false;
            for(int d=0;d<4;d++)
            {
                n=getNeighborIndex(i,j,d);
                if(n!=-1 && isOpen(n))
                {
                    uf.union(index,n);
                    hasN=true;
                }
            }
            if(i==0)
            {
                uf.union(index,topIndex);
            }
            if(hasN)
            {
                for(int b=gridSize-1;b>=gridSize-rowL;b--)
                {
                    if(isOpen(b) && uf.connected(topIndex,b))
                    {
                        uf.union(b,bottomIndex);
                        break;
                    }
                }
            }
            else if(gridSize==1)
            {
                uf.union(index,bottomIndex);
            }
        }
    }
    private int getIndex(int i,int j)
    {
        return i*rowL+j;
    }
     private int getNeighborIndex(int i, int j, int d) 
     {
        if (0 > d || 3 < d) {
            throw new java.lang.IllegalArgumentException(
                "Direction must be between 0 and 3"
            );
     }
        switch(d)
        {
            case 0:
                if(i==0){
                return -1;
            }
                return getIndex(i-1,j);
              case 1: 
                if (j+1 == rowL) {
                    return -1;
                }
                return getIndex(i, j+1);
            case 2:  
                if (1+i == rowL) {
                    return -1;
                }
                return getIndex(i+1, j);
            case 3:  
                if (0 == j) {
                    return -1;
                }
                return getIndex(i, j-1);
            default:
        }
        return -1;
    }
    private boolean isOpen(int index)
    {
        return grid[index];
    }
    public boolean isOpen(int i,int j)
    {
        checkInput(i,j);
        return uf.connected(topIndex,getIndex(i-1,j-1));
    }
    public boolean isFull(int i, int j) {
        checkInput(i, j);       
        return uf.connected(topIndex, getIndex(i-1, j-1));
    }
     public boolean percolates() {
        if (!percolates) {
            percolates = uf.connected(topIndex, bottomIndex);
        }
        return percolates;
    }
     public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 3);
    }
}