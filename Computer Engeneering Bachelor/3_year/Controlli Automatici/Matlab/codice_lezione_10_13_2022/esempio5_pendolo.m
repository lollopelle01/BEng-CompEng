%
% Esempio 5: Pendolo
%
close all; clear all; clc;

% parametri fisici del sistema
mass = 0.5;   % kg
length = 0.6; % metri
grav = 9.81;  % m/s^2
attr = 0.25;  % coefficiente d'attrito (b)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% INSERIRE:
%%% - Condizione iniziale del pendolo nelle variabili theta_init e theta_dot_init
%%% - Funzione che modella l'input nella variabile inp
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% intervallo di tempo
interv = [0 10]; % da 0 a 10 secondi

%% risoluzione equazione differenziale

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% INSERIRE: funzione che modella la dinamica nella variabile dyn, con argomenti (t, x)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% vettore delle condizioni iniziali
x0 = [theta_init; theta_dot_init];

% risolviamo l'equazione differenziale
[time, traj] = ode45(dyn, interv, x0);

%% plot

figure
plot(time,traj)
title('Traiettoria di stato del pendolo')
xlim(interv)
xlabel('tempo [s]')
ylabel('stato')
legend('angolo', 'velocità angolare')
grid on; zoom on; box on;
