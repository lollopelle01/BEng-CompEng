%
% Esempio 5: Pendolo
%
close all; clear all; clc;

% parametri fisici del sistema
mass = 0.5;   % kg
length = 0.6; % metri
grav = 9.81;  % m/s^2
attr = 0.25;  % coefficiente d'attrito (b)

% condizione iniziale del pendolo
theta_init     = deg2rad(30); % angolo: 30 gradi
theta_dot_init = 0; % velocità angolare: nulla

% input applicato: costante e pari a 2
inp = @(t) 2;

% intervallo di tempo
interv = [0 10]; % da 0 a 10 secondi

%% risoluzione equazione differenziale

% dinamica: ftilde(x, t)
dyn = @(t, x) [x(2);
    -grav/length*sin(x(1)) - attr/(mass*length^2)*x(2) + inp(t)/(mass*length^2)];

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
