%
% Esempio Jordan modi 
%
close all; clear all; clc;

% intervallo di tempo
interv = 0:0.1:20; % da 0 a 10 secondi con passo 0.1

% matrici del sistema
A = [-2 1; 0 -2];
B = [0; 1];
C = [1 0];
D = 0;

% state-space model e soluzione equazione
modello = ss(A, B, C, D);
uu = zeros(length(interv), 1); % input nullo (evoluzione libera)
x0_1 = [0.5;0]; % e^t
[~, TT, XX_1] = lsim(modello, uu, interv, x0_1);

x0_2 = [0;2]; % t*e^t
[~, ~, XX_2] = lsim(modello, uu, interv, x0_2);

plot(TT, XX_1(:,1)')
grid on; hold on;
plot(TT, XX_2(:,1)')
legend('exp(-2t)', 't*exp(-2t)');