%
% Esempio: Principio sovrapposizione effetti non vale per il pendolo
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

% input applicato: costante e pari a 10
inp_forced = @(t) 10; % evoluzione forzata
inp_free   = @(t) 0; % evoluzione libera

% intervallo di tempo
interv = 0:0.1:10; % da 0 a 10 secondi

%% risoluzione equazione differenziale

% dinamica: ftilde(x, t)
dyn_free   = @(t, x) [x(2);
    -grav/length*sin(x(1)) - attr/(mass*length^2)*x(2)];

dyn_forced = @(t, x) [x(2);
    -grav/length*sin(x(1)) - attr/(mass*length^2)*x(2) + inp_forced(t)];

% vettore delle condizioni iniziali
x0_free   = [theta_init; theta_dot_init];
x0_forced = zeros(2, 1);

% risolviamo l'equazione differenziale
[time_free, traj_free]     = ode45(dyn_free, interv, x0_free);
[time_forced, traj_forced] = ode45(dyn_forced, interv, x0_forced);
[time_full, traj_full]     = ode45(dyn_forced, interv, x0_free);

%% plot

figure;
plot(time_full, traj_free(:,1) + traj_forced(:,1))
hold on; grid on; zoom on; box on;
title('Traiettoria dell''angolo')
xlim([0 10])
xlabel('tempo [s]')
ylabel('angolo')
plot(time_full, traj_full(:,1))
legend('libera + forzata', 'totale')
