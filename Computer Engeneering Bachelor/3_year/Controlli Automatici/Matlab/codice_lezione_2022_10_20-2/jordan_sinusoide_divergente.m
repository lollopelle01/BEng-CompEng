%
% Esempio Sistema poli immaginari instabile
%
close all; clear all; clc;

% intervallo di tempo
interv = 0:0.1:20; % da 0 a 10 secondi con passo 0.1

% matrici del sistema
A = [0 -1 1 0; 1 0 0 1; 0 0 0 -1; 0 0 1 0];
B = [0; 0 ; 0 ;1];
C = [1 0 0 0];
D = 0;

% state-space model e soluzione equazione
modello = ss(A, B, C, D);
uu = zeros(length(interv), 1); % input nullo (evoluzione libera)
x0 = [1;1;1;0]; % modo instabile
% x0 = [1;0;0;0]; % modo stabile
[YY, TT, XX] = lsim(modello, uu, interv, x0);

plot(TT, XX(:,1)')
grid on