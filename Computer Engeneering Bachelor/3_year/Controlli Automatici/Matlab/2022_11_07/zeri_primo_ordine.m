clear all; close all; clc;

mu = 1;
T = 2;
alfa = -3;
k = 1;

s = tf('s');

G_1 = mu/(1+T*s)
modello_1 = ss(G_1);
fprintf("A=%.1f, B=%.1f, C=%.1f, D=%.1f\n",modello_1.A,modello_1.B,modello_1.C,modello_1.D)

pause
G_2 = mu*(1+alfa*T*s)/(1+T*s)
modello_2 = ss(G_2);
fprintf("A=%.1f, B=%.1f, C=%.1f, D=%.1f\n",modello_2.A,modello_2.B,modello_2.C,modello_2.D)

pause
step(G_2);
