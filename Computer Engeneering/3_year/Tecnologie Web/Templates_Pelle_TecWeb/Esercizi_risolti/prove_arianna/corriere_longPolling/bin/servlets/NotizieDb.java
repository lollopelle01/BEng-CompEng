����   7 �  servlets/NotizieDb  javax/servlet/http/HttpServlet serialVersionUID J ConstantValue        gson Lcom/google/gson/Gson; lastInteraction Ljava/time/LocalTime; <init> ()V Code
     LineNumberTable LocalVariableTable this Lservlets/NotizieDb; init  (Ljavax/servlet/ServletConfig;)V 
Exceptions  javax/servlet/ServletException
      com/google/gson/Gson
  	  " 
 
  $ % & getServletContext  ()Ljavax/servlet/ServletContext; ( 	dbnotizie * , + javax/servlet/ServletContext - . getAttribute &(Ljava/lang/String;)Ljava/lang/Object; 0 beans/NewsList
 / 	 / 3 4 5 news Ljava/util/List; 7 
beans/News 9 Economia ; pil = java/util/Date
 < 
 6 @  A 7(Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;)V C E D java/util/List F G add (Ljava/lang/Object;)Z I Politica K 	bu Meloni M Sport O basket * Q R S setAttribute '(Ljava/lang/String;Ljava/lang/Object;)V
 U W V java/time/LocalTime X Y now ()Ljava/time/LocalTime;	  [   conf Ljavax/servlet/ServletConfig; databasenotizie Lbeans/NewsList; StackMapTable doPost R(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V d java/io/IOException f attivo h j i %javax/servlet/http/HttpServletRequest k l getParameter &(Ljava/lang/String;)Ljava/lang/String;
 U n o p 	getMinute ()I	 / r s t valid Z h v w x 
getSession "()Ljavax/servlet/http/HttpSession; z | { javax/servlet/http/HttpSession }  
invalidate
 /  � � getNews ()Ljava/util/List; C � � � iterator ()Ljava/util/Iterator; � � � java/util/Iterator � � next ()Ljava/lang/Object;
 6 � � � 	isRecente (I)Z � � � � hasNext ()Z � � � &javax/servlet/http/HttpServletResponse � � 	getWriter ()Ljava/io/PrintWriter; C � � � toArray ()[Ljava/lang/Object;
  � � � toJson &(Ljava/lang/Object;)Ljava/lang/String;
 � � � java/io/PrintWriter � � println (Ljava/lang/String;)V request 'Ljavax/servlet/http/HttpServletRequest; response (Ljavax/servlet/http/HttpServletResponse; listNewsAggiornata Ljava/lang/String; ora n Lbeans/News; � java/lang/String doGet 
SourceFile NotizieDb.java !                
                 /     *� �                                         �*+� *� Y�  � !*� #'� ) � /M,� k� /Y� 1M,� 2� 6Y8:� <Y� >� ?� B W,� 2� 6YHJ� <Y� >� ?� B W,� 2� 6YLN� <Y� >� ?� B W*� #',� P *� T� Z�       .           #   + ! G " c #  % � ' � (         �       � \ ]   t ^ _  `    � � /  a b        c   �  	   �*� #'� ) � /N� /Y� 1:+e� g :� *� T� Z�� T:*� Z� m� md� � q+� u � y � D-� ~� � :� &� � � 6:<� �� � 2� B W� � ���� q,� � *� !� 2� � � �� ��       J    -  .  0 " 2 ' 3 . 4 / 6 4 8 E 9 K : V ; Y > s ? } @ � > � C � G � H    R    �       � � �    � � �   � ^ _   � � _  " � f �  4 f �   s  � �  `   N � / / / �� ) U�  	  h � / / � U  �  "�    h � / / �    � b        c    ?      �           M                 � �     � �   �    �