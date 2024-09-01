clear all;close all;clc;

mu = 10;
T = 0.1;


G1 = tf(mu);
bode(G1); grid on; zoom on; hold on;

s = tf('s');
G2 = 1/(1+T*s);
bode(G2);

G = mu/(1+T*s);
bode(G);