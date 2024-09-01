%
% Esempio Carrello con Control System Toolbox
%
close all; clear all; clc;

% parametri fisici del sistema
mass = 0.5; % kg
elas = 1;   % costante elastica [N/m]

% condizione iniziale del carrello
pos_init = 0; % [m]
vel_init = 1; % [m/s]

% input applicato: sinusoidale con periodo 5 secondi
inp = @(t) sin(2.*pi.*t./5);

% intervallo di tempo
interv = 0:0.1:10; % da 0 a 10 secondi con passo 0.1

%% creazione oggetto sistema e soluzione equazione differenziale

% matrici del sistema
A = [0 1; -elas/mass 0];
B = [0; 1/mass];
C = [1 0];
D = 0;

% state-space model
modello = ss(A, B, C, D);

% soluzione equazione
uu = inp(interv);
x0 = [pos_init; vel_init];
[YY, TT, XX] = lsim(modello, uu, interv, x0);

%% plot

figure;
plot(TT,XX)
title('Traiettoria di stato del carrello')
xlim([0, 10])
xlabel('tempo [s]')
ylabel('stato')
legend('posizione', 'velocità')
grid on; zoom on; box on;
