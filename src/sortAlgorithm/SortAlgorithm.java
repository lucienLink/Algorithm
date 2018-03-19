package sortAlgorithm;

public class SortAlgorithm {

    public static void main(String[] args) {
        int[] array = {3, 23, 88, 2, 37, 2, 16, 29, 5, 22};
        SortAlgorithm inst = new SortAlgorithm();
        int[] temp = new int[9];
        inst.baseSort(array, 0);
        System.out.print("排序结果:" + "\n");
        for (int i : array) {
            System.out.print(i + ",");
        }
    }

    //冒泡 两个数比较大小，较大的数下沉，较小的数冒起来
    public int[] bubbling(int[] array) {
        int temp = 0;
        for (int j = 0; j < array.length; j++)
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
        return array;
    }

    //选择 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；第二次遍历n-2个数，找到最小的数值与第二个元素交换
    public int[] select(int[] array) {
        int min = 0;
        int temp = 0;
        int index = 0;
        for (int j = 0; j < array.length; j++) {
            for (int i = j; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    min = array[i + 1];
                    index = i + 1;
                } else {
                    min = array[i];
                    index = i;
                }
            }
            temp = array[j];
            array[j] = min;
            array[index] = temp;
        }
        return array;
    }

    //插入 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序
    //插入时使用冒泡找到对应位置
    public int[] insert(int[] array) {
        int temp = 0;
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    //希尔排序 在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
    //然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序。
    public int[] shell(int[] array) {
        System.out.println("shell排序");
        int temp = 0;
        int increase = array.length;
        System.out.println("结果");
        while (true) {
            increase /= 2;
            System.out.println("间隔为：" + increase);
            for (int j = 0; j < increase; j++) {
                for (int i = j + increase; i < array.length; i += increase) {
                    for (int k = i; k > j; k -= increase) {
                        if (array[k] < array[k - increase]) { //每个子序列做插入排序(遍历子序列)，插入，实际上就是冒泡
                            temp = array[k];
                            array[k] = array[k - increase];
                            array[k - increase] = temp;
                        }
                    }
                }
            }
            for (int i : array) {
                System.out.print(i + ",");
            }
            System.out.print("\n");
            if (increase == 1) {
                break;
            }
        }
        return array;
    }

    //快速排序(分治法)(递归)
    //选择一个基准元素,通常选择第一个元素或者最后一个元素,
    //通过一趟排序讲待排序的记录分割成独立的两部分，其中一部分记录的元素值均比基准元素值小。另一部分记录的 元素值比基准值大。
    //此时基准元素在其排好序后的正确位置
    //然后分别对这两部分记录用同样的方法继续进行排序，直到整个序列有序。
    public static void quick(int array[], int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int i = startIndex;//从前向后的指针
        int j = endIndex;//从后向前的指针
        int key = array[startIndex];//选择第一个数为key

        while (i < j) {
            while (i < j && array[j] >= key) {
                //从右向左找第一个小于key的值
                j--;
            }
            if (i < j) {
                array[i] = array[j];
                i++;
            }

            while (i < j && array[i] < key) {
                //从左向右找第一个大于key的值
                i++;
            }

            if (i < j) {
                array[j] = array[i];
                j--;
            }
        }
        //当i==j的时候一次排序结束，递归排序子序列
        array[i] = key;
        quick(array, startIndex, i - 1);//递归调用
        quick(array, j + 1, endIndex);//递归调用
    }


    //归并排序
    public int[] merge(int[] array, int start, int end, int[] temp) {
        if (end - start >= 1) {
            int middle = (start + end) / 2;
            merge(array, start, middle, temp);//左半部分排好序
            merge(array, middle + 1, end, temp);//右半部分排好序
            mergeAndSort(array, start, end, middle, temp); //合并左右部分
        }
        return temp;
    }

    //合并start-middle与middle-end并按从大到小排序
    //temp为需要合并的两部分数组，合并后的结果存到array的对应位置
    public int[] mergeAndSort(int[] array, int start, int end, int middle, int[] temp) {
        int count = 0;
        int i = start;
        int m = middle;
        int j = middle + 1;
        int n = end;
        while (i <= middle && j <= end) {
            if (array[i] < array[j]) {
                temp[count++] = array[i++];
            } else {
                temp[count++] = array[j++];
            }
        }
        while (i <= m) {
            temp[count++] = array[i++];
        }
        while (j <= n) {
            temp[count++] = array[j++];
        }
        for (i = 0; i < count; i++) {
            array[start + i] = temp[i];
        }

        System.out.print("排序结果：");
        for (int x : temp) {
            System.out.print(x + ",");
        }
        System.out.print("\n");
        return temp;
    }

    //堆排序
    public int[] heapSort(int[] array) {
        int length = array.length;
        int temp = 0;
        //当length为0时排序完毕
        while (length >= 1) {
            //循环构造完全二叉树
            for (int i = length / 2; i >= 0; i--) {
                //构造大顶堆
                if (2 * i < length && array[2 * i] > array[i]) {
                    temp = array[2 * i];
                    array[2 * i] = array[i];
                    array[i] = temp;
                }
                if ((2 * i) + 1 < length && array[(2 * i) + 1] > array[i]) {
                    temp = array[(2 * i) + 1];
                    array[(2 * i) + 1] = array[i];
                    array[i] = temp;
                }
            }
            //取完全二叉树的一级父节点，值为当前最大值，与数组最后一位交换，length-1，再取剩余部分重复构造完全二叉树
            temp = array[0];
            array[0] = array[length - 1];
            array[length - 1] = temp;
            length--;
        }
        return array;
    }

    //基数排序(桶排序)
    //线根据个位数取数字放入桶中，个位为1放入一号桶，2则放入2号桶，以此类推
    //数组遍历完成后将桶中所有数据按顺序取出，进位(十位)再次按数字放入桶中，遍历完成再全部按顺序取出，直到所有数字的每一位都作为基数取过
    //最后将桶内数据按顺序取出到数组中，则完成排序
    public int[] baseSort(int[] array, int index) {
        int[][] bucket = new int[10][array.length];
        //初始化二维数组
        for (int row = 0; row < bucket.length; row++) {
            for (int column = 0; column < bucket[row].length; column++) {
                bucket[row][column] = -1;
            }
        }
        //最长数字长度
        int maxLength = 0;
        //取出数组中数字的最长长度
        for (int num : array) {
            if (maxLength < Integer.toString(num).length()) {
                maxLength = Integer.toString(num).length();
            }
        }
        //根据不同位的基数将数组中的数字放入二维数组中
        if (index <= maxLength) {
            for (int i : array) {
                int digital = getDegital(i, index);
                if (digital != -1) {
                    bucket[digital][getLastindex(bucket, digital)] = i;
                }
            }
            //将二维数组中的数据顺序输出到一维数组中
            toOneDimension(array, bucket);
            index++;
            //递归进位排序
            baseSort(array, index);
        }

        return array;
    }

    //获取位数的值,index从0开始  表示位数
    public int getDegital(int num, int index) {
        int[] base = {10, 100, 100, 1000, 10000};
        if (index == 0) {
            return num % 10;
        } else if (Integer.toString(num).length() <= 5) {
            return num / base[index - 1];
        } else {
            return -1;
        }
    }

    //顺序输出二维数组中的非空值到一维数组
    public int[] toOneDimension(int[] array, int[][] origin) {
        int index = 0;
        for (int row = 0; row < origin.length; row++) {
            for (int column = 0; column < origin[row].length; column++) {
                if (origin[row][column] != -1) {
                    array[index] = origin[row][column];
                    index++;
                }
            }
        }
        return array;
    }

    //取二维数组每行的第一个空位
    public int getLastindex(int[][] array, int row) {
        for (int i = 0; i < array[row].length; i++) {
            if (array[row][i] == -1) return i;
        }
        return -1;
    }
}
