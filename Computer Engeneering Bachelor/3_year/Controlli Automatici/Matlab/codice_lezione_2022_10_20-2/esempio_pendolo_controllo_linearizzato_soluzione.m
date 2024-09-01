close all; clear all; clc;

% parametri fisici del sistema
mm = 0.5;   % kg
ll = 0.6; % metri
gg = 9.81;  % m/s^2
bb = 0.25;  % coefficiente d'attrito (b)

% condizione iniziale del pendolo
theta_init     = deg2rad(179); % 
theta_dot_init = 0.0; % 

% vettore delle condizioni iniziali
x0 = [theta_init; theta_dot_init];

% intervallo di tempo
interv = 0:0.1:15; % da 0 a 10 secondi

%% calcolo matrici linearizzato e ingresso di controllo

x_eq = [pi;0];
A_eq = [0 1; gg/ll -bb/(mm*ll^2)];
B_eq = [0; 1/(mm*ll^2)];
eigs_feed = [-1 -2];
K = place(A_eq,B_eq,eigs_feed);

inp = @(x) -K*(x-x_eq);

%% risoluzione equazione differenziale
% dinamica: f(t, x)
dyn = @(t, x) [x(2);
    -gg/ll*sin(x(1)) - bb/(mm*ll^2)*x(2) + inp(x)/(mm*ll^2)];

% risolviamo l'equazione differenziale
[time, traj] = ode45(dyn, interv, x0);


%% Animation

xx=traj';
pivotPoint = [2,2];
radius = .1; %of the bob

position = pivotPoint - (ll*...
                     [-sin(xx(1,1)) cos(xx(1,1))]);
 
rectHandle = rectangle('Position',[(position - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob

lineHandle = line([pivotPoint(1) position(1)],...
    [pivotPoint(2) position(2)], 'LineWidth',2); %pendulum rod
axis equal
%Run simulation, all calculations are performed in cylindrical coordinates
for tt=1:length(interv)
    
    grid on
 
    position = pivotPoint - (ll*...
                     [-sin(xx(1,tt)) cos(xx(1,tt))]);
 
    %Update figure with new position info
    set(rectHandle,'Position',[(position - radius/2) radius radius]);
    set(lineHandle,'XData',[pivotPoint(1) position(1)],'YData',...
        [pivotPoint(2) position(2)]);
    axesHandle = gca;
xlim(axesHandle, [(pivotPoint(1) - ll - radius ) ...
                            (pivotPoint(1) + ll + radius )] );
ylim(axesHandle, [(pivotPoint(2) - ll - radius) ...
                            (pivotPoint(2) +ll + radius)] );
 drawnow; %Forces MATLAB to render the pendulum
 if tt==1
     pause(0.5)
 else
    pause(0.05) 
 end
end
