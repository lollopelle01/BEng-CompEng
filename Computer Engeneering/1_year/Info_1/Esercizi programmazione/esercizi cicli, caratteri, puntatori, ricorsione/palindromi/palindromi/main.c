//
//  main.c
//  palindromi
//
//  Created by Lorenzo Pellegrino on 06/11/2020.
//

#define _CRT_SECURE_NO_WARNINGS
int palindrome()
{
   char before;
   char after;
   int result;
   before = getchar();
   if (before == '@')
        return 1;
   else
   {
    result = palindrome();
    if (result)
    {
        after = getchar();
       if (before == after)
               result = 1;
       else
                result = 0;

    }
        return result;
   }
}

int main()
{
   int result;
    printf("Inserire la sequenza (elemento centrale: '@'):\n"); result = palindrome();
    if (result)
    {
    printf("La sequenza inserita e' una palindrome\n");
    }
    else
    {
    printf("La sequenza inserita NON e' una palindrome\n");
    
    }
    return 0;
    
}

